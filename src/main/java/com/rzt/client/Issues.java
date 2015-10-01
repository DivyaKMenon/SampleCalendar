package com.rzt.client;

import java.util.List;

import com.atlassian.jira.rest.client.api.domain.Issue;

public class Issues {
	private List<Issue> issues;

	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

}
