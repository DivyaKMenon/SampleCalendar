package com.rzt.client;

public class Jira {
	private String sprint;
	private String assignee;
	private String sprintType;
	private String project;
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getSprint() {
		return sprint;
	}

	public void setSprint(String sprint) {
		this.sprint = sprint;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getSprintType() {
		return sprintType;
	}

	public void setSprintType(String sprintType) {
		this.sprintType = sprintType;
	}

}
