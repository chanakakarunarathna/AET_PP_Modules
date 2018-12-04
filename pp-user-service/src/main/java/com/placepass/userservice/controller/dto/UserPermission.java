package com.placepass.userservice.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class UserPermission {
	
	private String name;
	
	private List<String> permissions = new ArrayList<String>();
	
	
	public UserPermission(){
		super();
	}
	
	public UserPermission(String name, List<String> permissions) {
		super();
		this.name = name;
		this.permissions = permissions;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * @return the permissions
	 */
	public List<String> getPermissions() {
		return permissions;
	}
	
}
