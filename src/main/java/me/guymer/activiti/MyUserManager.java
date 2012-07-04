package me.guymer.activiti;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import me.guymer.activiti.users.UserService;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyUserManager extends UserManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MyUserManager.class);
	
	@Inject
	private UserService userService;

	@Override
	public User createNewUser(String userId) {
		throw new ActivitiException("My user manager doesn't support creating a new user");
	}

	@Override
	public void insertUser(User user) {
		throw new ActivitiException("My user manager doesn't support inserting a new user");
	}

	@Override
	public void updateUser(User updatedUser) {
		throw new ActivitiException("My user manager doesn't support updating a user");
	}

	@Override
	public void deleteUser(String userId) {
		throw new ActivitiException("My user manager doesn't support deleting a user");
	}

	@Override
	public UserEntity findUserById(String userId) {
		LOGGER.info("findUserById {}", userId);
		
		me.guymer.activiti.users.User user = userService.findUserById(userId);
		
		UserEntity userEntity = new UserEntity();
		userEntity.setId(Integer.toString(user.getId()));
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getSurname());
		userEntity.setEmail(user.getEmail());
		userEntity.setPassword(user.getPassword());
		
		LOGGER.info("findUserById {}", userEntity.getEmail());
		
		return userEntity;
	}

	@Override
	public List<User> findUserByQueryCriteria(Object query, Page page) {

		/*List<User> userList = new ArrayList<User>();

		UserQueryImpl userQuery = (UserQueryImpl) query;

		if (StringUtils.isNotEmpty(userQuery.getId())) {

			userList.add(findUserById(userQuery.getId()));

			return userList;

		} else if (StringUtils.isNotEmpty(userQuery.getLastName())) {

			userList.add(findUserById(userQuery.getLastName()));

			return userList;

		} else {

			// TODO: get all users from your identity domain and convert them to
			// List<User>

			return null;

		} // TODO: you can add other search criteria that will allow extended
			// support using the Activiti engine API*/
		return Collections.emptyList();
	}

	@Override
	public long findUserCountByQueryCriteria(Object query) {
		return findUserByQueryCriteria(query, null).size();
	}

	@Override
	public Boolean checkPassword(String userId, String password) {
		// TODO: check the password in your domain and return the appropriate

		return false;
	}

}
