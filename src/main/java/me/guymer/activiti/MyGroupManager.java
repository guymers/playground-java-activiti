package me.guymer.activiti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import me.guymer.activiti.groups.GroupService;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyGroupManager extends GroupManager {

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
	public void updateGroup(Group updatedGroup) {
		throw new ActivitiException("My group manager doesn't support updating a new group");
	}

	@Override
	public void deleteGroup(String groupId) {
		throw new ActivitiException("My group manager doesn't support deleting a new group");
	}
	
	@Override
	public GroupEntity findGroupById(String groupId) {
		LOGGER.info("findGroupById {}", groupId);
		
		me.guymer.activiti.groups.Group group = groupService.findGroupById(groupId);
		
		GroupEntity groupEntity = groupToGroupEntity(group);
		
		LOGGER.info("findGroupById {}", groupEntity.getName());
		
		return groupEntity;
	}

	@Override
	public long findGroupCountByQueryCriteria(Object query) {
		//return findGroupByQueryCriteria(query, null).size();
		return 1;
	}

	@Override
	public List<Group> findGroupByQueryCriteria(Object query, Page page) {

		/*List<Group> groupList = new ArrayList<Group>();

		GroupQueryImpl groupQuery = (GroupQueryImpl) query;

		if (StringUtils.isNotEmpty(groupQuery.getId())) {
			GroupEntity singleGroup = findGroupById(groupQuery.getId());

			groupList.add(singleGroup);

			return groupList;
		} else if (StringUtils.isNotEmpty(groupQuery.getName())) {
			GroupEntity singleGroup = findGroupById(groupQuery.getId());

			groupList.add(singleGroup);

			return groupList;
		} else if (StringUtils.isNotEmpty(groupQuery.getUserId())) {
			return findGroupsByUser(groupQuery.getUserId());
		} else {
			// TODO: get all groups from your identity domain and convert them
			// to List<Group>

			return null;
		} // TODO: you can add other search criteria that will allow extended
			// support using the Activiti engine API*/
		return Collections.emptyList();
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
	
	private GroupEntity groupToGroupEntity(me.guymer.activiti.groups.Group group) {
		GroupEntity groupEntity = new GroupEntity();
		groupEntity.setId(Integer.toString(group.getId()));
		groupEntity.setName(group.getName());
		
		return groupEntity;
	}

}
