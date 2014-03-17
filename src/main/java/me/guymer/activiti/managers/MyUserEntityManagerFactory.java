package me.guymer.activiti.managers;

import javax.inject.Inject;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.stereotype.Service;

@Service
public class MyUserEntityManagerFactory implements SessionFactory {

	@Inject
	private MyUserManager myUserManager;

	@Override
	public Class<?> getSessionType() {
		return UserEntityManager.class;
	}

	@Override
	public Session openSession() {
		return myUserManager;
	}
}
