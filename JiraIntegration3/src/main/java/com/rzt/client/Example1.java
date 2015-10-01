package com.rzt.client;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicProject;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.greenhopper.GreenHopperClient;
import net.rcarz.jiraclient.greenhopper.RapidView;
import net.rcarz.jiraclient.greenhopper.Sprint;

public class Example1 {

	// private static String URL = "http://localhost:8888/";
	// public static String USERNAME = "dmenon";
	// public static String PASSWORD = "divya@TestJira";
	public static String URL = "http://razorthink.atlassian.net";
	public static String USERNAME = "keerthi";
	public static String PASSWORD = "raz0rth1nk";
	public List<RapidView> allRapidView = null;
	public List<Sprint> allSprints = null;
	public List<Issue> allIssues = null;
	public List<Issue> datedIssues;
	public List<Issue> sprintIssues;
	public List<Issue> sprintByNameIssues;
	public List<Issue> sprintByBacklog;
	public List<String> projects = new ArrayList<String>();
	public List<String> developers;

	public Example1(User userObject) throws IOException, JiraException, InterruptedException, ExecutionException {
		final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
		final JiraRestClient restClient = factory.createWithBasicHttpAuthentication(URI.create(URL),
				userObject.getUsername(), userObject.getPassword());
		Iterator<BasicProject> proj = restClient.getProjectClient().getAllProjects().claim().iterator();
		while (proj.hasNext()) {
			BasicProject p = proj.next();
			/*
			 * System.out.println("uri " + p.getSelf()); Iterator<ProjectRole>
			 * roles =
			 * restClient.getProjectRolesRestClient().getRoles(p.getSelf()).get(
			 * ).iterator(); while (roles.hasNext()) { ProjectRole role =
			 * roles.next(); System.out.println("role " + role.getName());
			 * Iterator<RoleActor> actors = role.getActors().iterator(); while
			 * (actors.hasNext()) { RoleActor actor = actors.next();
			 * System.out.println("actor " + actor.getName()); } }
			 */
			projects.add(p.getKey());
		}
		BasicCredentials creds = new BasicCredentials(userObject.getUsername(), userObject.getPassword());
		JiraClient jira = new JiraClient(URL, creds);
		GreenHopperClient gh = new GreenHopperClient(jira);
		allRapidView = gh.getRapidViews();
		Iterator<RapidView> it = allRapidView.iterator();
		while (it.hasNext()) {
			RapidView rv = it.next();
			// if (rv.toString().equals("BrainBlox")) {
			allSprints = rv.getSprints();
			// }
		}
		/*
		 * try { // final Issue issue = //
		 * restClient.getIssueClient().getIssue("MYP-1").claim(); //
		 * System.out.println(issue); get issue count // int count =
		 * restClient.getSearchClient().searchJql("project // =Inblox AND sprint
		 * = 18").claim().getTotal(); // System.out.println("Issue count ** " +
		 * count); get issues based on project name allIssues = (List<Issue>)
		 * restClient.getSearchClient().searchJql("project = Inblox"
		 * ).claim().getIssues(); get issues based on created date datedIssues =
		 * (List<Issue>) restClient.getSearchClient() .searchJql(
		 * "project = Inblox AND created >= '2015/02/26' AND created < '2015/02/27'"
		 * ).claim() .getIssues(); get issues for opens sprints sprintIssues =
		 * (List<Issue>) restClient.getSearchClient() .searchJql(
		 * "project = Inblox AND sprint in openSprints()").claim().getIssues();
		 * get issues by sprint name sprintByNameIssues = (List<Issue>)
		 * restClient.getSearchClient() .searchJql(
		 * "project = Inblox AND sprint = 18").claim().getIssues(); // projects
		 * = (List<Project>) // restClient.getProjectClient().getAllProjects();
		 * sprintByBacklog = (List<Issue>) restClient.getSearchClient()
		 * .searchJql("project = Inblox AND status = 'QA BACKLOG'"
		 * ).claim().getIssues(); System.out.println(
		 * "listing all issues..........."); } finally { restClient.close(); }
		 */
	}

	public Example1(/* String sprint, String assignee, String sprintType */ Jira jiraObject)
			throws IOException, JiraException, InterruptedException, ExecutionException {
		final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
		final JiraRestClient restClient = factory.createWithBasicHttpAuthentication(URI.create(URL),
				jiraObject.getUsername(), jiraObject.getPassword());
				// Iterator<BasicProject> proj =
				// restClient.getProjectClient().getAllProjects().claim().iterator();
				// while (proj.hasNext()) {
				// BasicProject p = proj.next();
				// System.out.println("uri " + p.getSelf());
				// Iterator<ProjectRole> roles =
				// restClient.getProjectRolesRestClient().getRoles(p.getSelf()).get().iterator();
				// while (roles.hasNext()) {
				// ProjectRole role = roles.next();
				// System.out.println("role " + role.getName());
				// Iterator<RoleActor> actors = role.getActors().iterator();
				// while (actors.hasNext()) {
				// RoleActor actor = actors.next();
				// System.out.println("actor " + actor.getName());
				// }
				// }
				// }

		/* Print the name of all current and past sprints */
		try {
			if (jiraObject.getSprintType().equals("open")) {
				allIssues = (List<Issue>) restClient.getSearchClient()
						.searchJql("project = '" + jiraObject.getProject()
								+ "' AND sprint in openSprints() AND sprint = '" + jiraObject.getSprint()
								+ "' AND assignee = '" + jiraObject.getAssignee() + "'")
						.claim().getIssues();
				System.out.println("listing open sprints");
			} else if (jiraObject.getSprintType().equals("closed")) {
				allIssues = (List<Issue>) restClient.getSearchClient()
						.searchJql("project = '" + jiraObject.getProject()
								+ "' AND sprint in closedSprints() AND sprint = '" + jiraObject.getSprint()
								+ "' AND assignee = '" + jiraObject.getAssignee() + "'")
						.claim().getIssues();
				System.out.println("listing closed sprints");
			} else {
				allIssues = (List<Issue>) restClient
						.getSearchClient().searchJql("project = '" + jiraObject.getProject() + "' AND sprint = '"
								+ jiraObject.getSprint() + "' AND assignee = '" + jiraObject.getAssignee() + "'")
						.claim().getIssues();
				System.out.println("listing open/closed sprints " + allIssues.size());
			}
		} finally {
			restClient.close();
		}
	}

	public List<Sprint> getAllSprints() {
		return this.allSprints;
	}

	public List<Issue> getAllIssues() {
		return this.allIssues;
	}

	public List<Issue> getAllIssuesByDate() {
		return this.datedIssues;
	}

	public List<Issue> getAllIssuesBySprint() {
		return this.sprintIssues;
	}

	public List<Issue> getAllIssuesBySprintName() {
		return this.sprintIssues;
	}

	public List<Issue> getAllIssuesByBacklog() {
		return this.sprintByBacklog;
	}

	public List<String> getAllProjects() {
		return this.projects;
	}

}
