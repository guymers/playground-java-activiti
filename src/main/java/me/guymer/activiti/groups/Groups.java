package me.guymer.activiti.groups;

import org.springframework.stereotype.Component;

/**
 * group names to ids
 */
@Component
public class Groups {

	private final String user = "1";
	private final String leader = "2";
	private final String manager = "3";

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
