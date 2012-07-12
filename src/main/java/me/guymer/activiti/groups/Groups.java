package me.guymer.activiti.groups;

import org.springframework.stereotype.Component;

/**
 * group names to ids
 */
@Component
public class Groups {
	
	private String user = "1";
	private String leader = "2";
	private String manager = "3";
	
	public String getUser() {
		return user;
	}
	
	public String getLeader() {
		return leader;
	}
	
	public String getManager() {
		return manager;
	}
	
}
