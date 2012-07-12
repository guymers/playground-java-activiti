package me.guymer.activiti.managers;

import javax.inject.Inject;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupManager;
import org.springframework.stereotype.Service;

@Service
public class MyGroupManagerFactory implements SessionFactory {
	
	@Inject
	private MyGroupManager myGroupManager;

	@Override
	public Class<?> getSessionType() {
		return GroupManager.class;
	}

	@Override
	public Session openSession() {
		return myGroupManager;
	}
}
