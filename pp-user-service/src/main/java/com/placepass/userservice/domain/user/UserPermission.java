package com.placepass.userservice.domain.user;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_permissions")
public class UserPermission {
	@Id
	private String id;
	private String name;
	private List<String> permissions;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the permissions
	 */
	public List<String> getPermissions() {
		return permissions;
	}
	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	
}
