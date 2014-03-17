package me.guymer.activiti.managers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.guymer.activiti.users.UserService;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyUserManager extends UserEntityManager {

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
	public void updateUser(UserEntity updatedUser) {
		throw new ActivitiException("My user manager doesn't support updating a user");
	}

	@Override
	public void deleteUser(String userId) {
		throw new ActivitiException("My user manager doesn't support deleting a user");
	}

	@Override
	public UserEntity findUserById(String userId) {
		LOGGER.info("findUserById {}", userId);

		me.guymer.activiti.users.User user = userService.findById(userId);

		UserEntity userEntity = userToUserEntity(user);

		LOGGER.info("findUserById {}", userEntity.getEmail());

		return userEntity;
	}

	@Override
	public UserQuery createNewUserQuery() {
		LOGGER.info("createNewUserQuery");
		// TODO Auto-generated method stub
		return super.createNewUserQuery();
	}

	@Override
	public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
		LOGGER.info("findUserByQueryCriteria");

		List<User> users = new ArrayList<User>();

		List<me.guymer.activiti.users.User> myUsers = userService.getUsers(query, page);

		for (me.guymer.activiti.users.User user : myUsers) {
			UserEntity userEntity = userToUserEntity(user);

			users.add(userEntity);
		}

		return users;
	}

	@Override
	public long findUserCountByQueryCriteria(UserQueryImpl query) {
		LOGGER.info("findUserCountByQueryCriteria");
		// TODO replace with count(*) from database
		List<User> users = findUserByQueryCriteria(query, null);

		return users.size();
	}

	@Override
	public List<Group> findGroupsByUser(String userId) {
		LOGGER.info("findGroupsByUser");
		// TODO Auto-generated method stub
		return super.findGroupsByUser(userId);
	}

	@Override
	public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
		LOGGER.info("findUserInfoByUserIdAndKey");
		// TODO Auto-generated method stub
		return super.findUserInfoByUserIdAndKey(userId, key);
	}

	@Override
	public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
		LOGGER.info("findUserInfoKeysByUserIdAndType");
		// TODO Auto-generated method stub
		return super.findUserInfoKeysByUserIdAndType(userId, type);
	}

	@Override
	public List<User> findPotentialStarterUsers(String proceDefId) {
		LOGGER.info("findPotentialStarterUsers");
		// TODO Auto-generated method stub
		return super.findPotentialStarterUsers(proceDefId);
	}

	@Override
	public Boolean checkPassword(String userId, String password) {
		// TODO: check the password in your domain and return the appropriate

		return false;
	}

	private UserEntity userToUserEntity(me.guymer.activiti.users.User user) {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(Integer.toString(user.getId()));
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getSurname());
		userEntity.setEmail(user.getEmail());
		userEntity.setPassword(user.getPassword());

		return userEntity;
	}
}
