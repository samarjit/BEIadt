package com.ycs.user;

import java.io.Serializable;

public class Role implements Serializable{
 
	private static final long serialVersionUID = 1L;
	private String roleId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public String toString(){
		return roleId;
	}
}
