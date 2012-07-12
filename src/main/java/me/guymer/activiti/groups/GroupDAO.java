package me.guymer.activiti.groups;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import me.guymer.activiti.AbstractRepository;
import me.guymer.activiti.users.User;
import me.guymer.activiti.users.User_;

import org.springframework.stereotype.Repository;

@Repository
public class GroupDAO extends AbstractRepository<Group> {

	public GroupDAO() {
		super(Group.class);
	}
	
	public List<Group> getByUserId(int userId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
		
		Root<Group> group = criteriaQuery.from(Group.class);
		SetJoin<Group, User> user = group.join(Group_.users);
		
		criteriaQuery.select(group);
		criteriaQuery.where(criteriaBuilder.equal(user.get(User_.id), userId));
		
		TypedQuery<Group> query = entityManager.createQuery(criteriaQuery);
		
		return query.getResultList();
	}
	
}
