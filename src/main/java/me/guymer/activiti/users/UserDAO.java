package me.guymer.activiti.users;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import me.guymer.activiti.AbstractRepository;

import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends AbstractRepository<User> {

	public UserDAO() {
		super(User.class);
	}

	public List<User> getUsers(UserQueryImpl userQuery, Page page) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

		Root<User> user = criteriaQuery.from(User.class);

		criteriaQuery.select(user);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (userQuery.getFirstName() != null) {
			Predicate firstNameEqual = criteriaBuilder.equal(user.<String>get("firstName"), userQuery.getFirstName());

			predicates.add(firstNameEqual);
		}

		if (userQuery.getFirstNameLike() != null) {
			Predicate firstNameEqual = criteriaBuilder.like(user.<String>get("firstName"), userQuery.getFirstNameLike());

			predicates.add(firstNameEqual);
		}

		if (userQuery.getLastName() != null) {
			Predicate lastNameEqual = criteriaBuilder.equal(user.<String>get("surname"), userQuery.getLastName());

			predicates.add(lastNameEqual);
		}

		if (userQuery.getLastNameLike() != null) {
			Predicate lastNameEqual = criteriaBuilder.like(user.<String>get("surname"), userQuery.getLastNameLike());

			predicates.add(lastNameEqual);
		}

		criteriaQuery.where(predicates.toArray(new Predicate[]{}));

		TypedQuery<User> query = entityManager.createQuery(criteriaQuery);

		if (page != null) {
			query.setFirstResult(page.getFirstResult());
			query.setMaxResults(page.getMaxResults());
		}

		return query.getResultList();
	}
}
