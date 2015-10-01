package com.rzt.client;

import java.io.IOException;
import java.net.URI;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

public class Example1 {

	private static URI jiraServerUri = URI.create("http://localhost:8888/");
	public static String URL = "http://razorthink.atlassian.net";
	public static String USERNAME = "keerthi";
	public static String PASSWORD = "raz0rth1nk";
	public Example1() throws IOException
	{
		final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
		final JiraRestClient restClient = factory.createWithBasicHttpAuthentication(
				URI.create(URL), USERNAME, PASSWORD);
		try
		{
			System.out.println("I am here");
			final Issue issue = restClient.getIssueClient().getIssue("IN-138").claim();
			System.out.println(issue);
		}
		finally
		{
			restClient.close();
		}
	}

}
//
// import static com.google.common.collect.Iterables.transform;
// import java.lang.reflect.InvocationTargetException;
// import java.net.URI;
// import java.util.List;
// import com.atlassian.jira.rest.client.api.IssueRestClient;
// import com.atlassian.jira.rest.client.api.JiraRestClient;
// import com.atlassian.jira.rest.client.api.domain.BasicIssue;
// import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
// import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
// import
// com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
// import com.atlassian.util.concurrent.Promise;
// import com.google.common.base.Function;
// import com.google.common.base.Joiner;
// import com.google.common.collect.Lists;
//
/// **
// * This example shows how to create many issues using asynchronous API.
// *
// * @since v2.0
// */
// public class Example1
//
// {
//
// public Example1() throws InvocationTargetException
// {
// // private static URI jiraServerUri =
// // URI.create("http://localhost:8080/jira");
// final AsynchronousJiraRestClientFactory factory = new
// AsynchronousJiraRestClientFactory();
// JiraRestClient restClient;
// restClient =
// factory.createWithBasicHttpAuthentication(URI.create("http://localhost:8888/"),
// "divyakjaya@gmail.com", "divya@TestJira");
// factory.create(serverUri, authenticationHandler)
// System.out.println("login");
// try
// {
// final List<Promise<BasicIssue>> promises = Lists.newArrayList();
// final IssueRestClient issueClient = restClient.getIssueClient();
//
// System.out.println("Sending issue request");
// for( int i = 0; i < 100; i++ )
// {
// final String summary = "NewIssue#" + i;
// final IssueInput newIssue = new IssueInputBuilder("TST", 1L,
// summary).build();
// System.out.println("\tCreating: " + summary);
// promises.add(issueClient.createIssue(newIssue));
// }
//
// System.out.println("Collecting responses...");
// final Iterable<BasicIssue> createdIssues = transform(promises,
// new Function<Promise<BasicIssue>, BasicIssue>() {
//
// @Override
// public BasicIssue apply( Promise<BasicIssue> promise )
// {
// return promise.claim();
// }
// });
//
// System.out.println("Created issues:\n" +
// Joiner.on("\n").join(createdIssues));
// }
// catch( Exception e )
// {
// // TODO Auto-generated catch block
// System.out.println("Exception " + e);
// e.printStackTrace();
// }
// }
//
// }
