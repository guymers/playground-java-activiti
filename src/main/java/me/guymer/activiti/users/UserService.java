package me.guymer.activiti.users;

import java.util.List;

import javax.inject.Inject;

import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Inject
	private UserDAO userDao;
	
	public User findById(String id) {
		int userId = Integer.parseInt(id);
		
		return userDao.get(userId);
	}
	
	public List<User> getUsers(UserQueryImpl query, Page page) {
		return userDao.getUsers(query, page);
	}
}
