package me.guymer.activiti.groups;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

@Service
public class GroupService {

	@Inject
	private GroupDAO groupDao;

	public Group findById(String id) {
		int groupId = Integer.parseInt(id);

		return groupDao.get(groupId);
	}

	public List<Group> findGroupsByUserId(String id) {
		return groupDao.getByUserId(Integer.parseInt(id));
	}
}
