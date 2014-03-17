package me.guymer.activiti.managers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.guymer.activiti.groups.GroupService;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyGroupManager extends GroupEntityManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyGroupManager.class);

	@Inject
	private GroupService groupService;

	@Override
	public Group createNewGroup(String groupId) {
		throw new ActivitiException("My group manager doesn't support creating a new group");
	}

	@Override
	public void insertGroup(Group group) {
		throw new ActivitiException("My group manager doesn't support inserting a new group");
	}

	@Override
	public void updateGroup(GroupEntity updatedGroup) {
		throw new ActivitiException("My group manager doesn't support updating a group");
	}

	@Override
	public void deleteGroup(String groupId) {
		throw new ActivitiException("My group manager doesn't support deleting a group");
	}

	@Override
	public List<Group> findGroupsByUser(String userLogin) {
		List<Group> groups = new ArrayList<Group>();

		List<me.guymer.activiti.groups.Group> myGroups = groupService.findGroupsByUserId(userLogin);

		for (me.guymer.activiti.groups.Group group : myGroups) {
			GroupEntity groupEntity = groupToGroupEntity(group);

			groups.add(groupEntity);
		}

		return groups;
	}

	@Override
	public GroupQuery createNewGroupQuery() {
		LOGGER.info("createNewGroupQuery");
		// TODO Auto-generated method stub
		return super.createNewGroupQuery();
	}

	@Override
	public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
		LOGGER.info("findGroupByQueryCriteria");
		// TODO Auto-generated method stub
		return super.findGroupByQueryCriteria(query, page);
	}

	@Override
	public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
		LOGGER.info("findGroupCountByQueryCriteria");
		// TODO Auto-generated method stub
		return super.findGroupCountByQueryCriteria(query);
	}

	private GroupEntity groupToGroupEntity(me.guymer.activiti.groups.Group group) {
		GroupEntity groupEntity = new GroupEntity();
		groupEntity.setId(Integer.toString(group.getId()));
		groupEntity.setName(group.getName());

		return groupEntity;
	}
}
