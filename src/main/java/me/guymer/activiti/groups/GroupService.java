package me.guymer.activiti.groups;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

@Service
public class GroupService {

	@Inject
	private GroupDAO groupDao;
	
	public Group findGroupById(String id) {
		return groupDao.getGroupById(Integer.parseInt(id));
	}
	
	public List<Group> findGroupsByUserId(String id) {
		return groupDao.getGroupsByUserId(Integer.parseInt(id));
	}

}
