package com.rzt.client;

import java.net.URI;
import java.util.Arrays;
import com.atlassian.jira.rest.client.api.GetCreateIssueMetadataOptions;
import com.atlassian.jira.rest.client.api.GetCreateIssueMetadataOptionsBuilder;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.BasicProject;
import com.atlassian.jira.rest.client.api.domain.CimFieldInfo;
import com.atlassian.jira.rest.client.api.domain.CimIssueType;
import com.atlassian.jira.rest.client.api.domain.CimProject;
import com.atlassian.jira.rest.client.api.domain.CustomFieldOption;
import com.atlassian.jira.rest.client.api.domain.EntityHelper;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.atlassian.jira.rest.client.api.domain.input.ComplexIssueInputFieldValue;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

/**
 * This demo is for people who just want to pull in the dependencies of the JIRA
 * API and figure out how to use it independent of this project.
 * 
 * This will create an issue in a random project to get started
 * 
 * @author sizu
 *
 */
public class Demo {

	public static String URL = "razorthink.atlassian.net";
	public static String USERNAME = "keerthi	";
	public static String PASSWORD = "raz0rth1nk";

	public static void main( String[] args )
	{
		System.out.println("Creating issue");
		// Connect to JIRA
		final JiraRestClientFactory jiraRestClientFactory = new AsynchronousJiraRestClientFactory();
		JiraRestClient jiraRestClient = jiraRestClientFactory.createWithBasicHttpAuthentication(URI.create(URL),
				USERNAME, PASSWORD);

		// Automatically Generate Project Key
		String projectKey = null;
		try
		{
			Promise<Iterable<BasicProject>> projects = jiraRestClient.getProjectClient().getAllProjects();
			BasicProject project = projects.get().iterator().next();
			projectKey = project.getKey();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		// Automatically Generate Issue Type ID
		Long issueTypeID = null;
		try
		{
			Promise project = jiraRestClient.getProjectClient().getProject(projectKey);
			IssueType issueType = ((CimProject) project.get()).getIssueTypes().iterator().next();
			issueTypeID = issueType.getId();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		// Start creating an issue
		IssueInputBuilder issueBuilder = new IssueInputBuilder(projectKey, issueTypeID);
		issueBuilder.setFieldValue("labels", Arrays.asList("DirectJIRAAPIUsageDemo"));

		// Grab the meta data to fill in required fields
		GetCreateIssueMetadataOptions options = new GetCreateIssueMetadataOptionsBuilder()
				.withExpandedIssueTypesFields().withProjectKeys(projectKey).build();

		Iterable projects = jiraRestClient.getIssueClient().getCreateIssueMetadata(options).claim();

		CimProject project = (CimProject) projects.iterator().next();
		CimIssueType issueType = EntityHelper.findEntityById(project.getIssueTypes(), issueTypeID);

		for( String key : issueType.getFields().keySet() )
		{
			CimFieldInfo issueField = issueType.getFields().get(key);
			String fieldID = issueField.getId();
			if( fieldID.equals("project") )
			{ // Skip the project field since it is already filled when
				// issueBuilder was created
				continue;
			}

			if( issueField.isRequired() )
			{
				try
				{
					// Get allowed value for the required field
					String allowedValue = null;
					for( Object object : issueField.getAllowedValues() )
					{
						if( object instanceof CustomFieldOption )
						{
							CustomFieldOption cfo = (CustomFieldOption) object;
							allowedValue = "" + cfo.getId(); // Convert Long to
							// String format
						}
					}

					ComplexIssueInputFieldValue value = ComplexIssueInputFieldValue.with("id", allowedValue);
					if( issueField.getSchema().getType().equals("array") )
					{
						issueBuilder.setFieldValue(fieldID, Arrays.asList(value));
					}
					else
					{
						issueBuilder.setFieldValue(fieldID, value);
					}
				}
				catch( Exception ex )
				{
					// Does not have allowed value
					issueBuilder.setFieldValue(fieldID, "Test");
				}
			}
		}

		// Create Issue
		IssueInput newIssue = issueBuilder.build();
		BasicIssue basicIssue = jiraRestClient.getIssueClient().createIssue(newIssue).claim();

		// Close connection
		try
		{
			jiraRestClient.close();
		}
		catch( Exception ex )
		{
		}
		System.out.println("Key:" + basicIssue.getKey() + " ID:" + basicIssue.getId());
	}
}