package com.ycs.user;

import java.util.List;

public class RoleRightsMap {
	private String roleId;
	private List<Task> tasks;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public List<Task> getTasks() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

}
