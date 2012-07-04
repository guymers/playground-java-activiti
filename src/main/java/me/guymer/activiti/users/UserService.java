package me.guymer.activiti.users;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Inject
	private UserDAO userDao;
	
	public User findUserById(String id) {
		return userDao.getUserById(Integer.parseInt(id));
	}

}
