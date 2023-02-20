package com.sahib.learnAPIAutomation;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class JiraAPI {
	
	private String projectId = null; 
	private String storyMetaId = null;
	private String authorization = "Basic Z2lsbC5zYWhpYmdpbGxAZ21haWwuY29tOkFUQVRUM3hGZkdGMHdpQ3Q5RDZiOHFyTmdDUmhoSWJEZEszZmZTMWY5a2ZIcnJJblNwdk9BaFFkcXFHSWJ5RHBSQ21kUFVEWHRJWmFiaWYtX3hjekY0cDJ2b25INVJTMEQxa25oMl9oRV9IenhUU1Z1ZzZJNUFTMEZQVlhxdUE3Y0gyeDgzT0ZYRHJZd3VZczY1UDdLemM0RnN2NFczT1MtQzBHNnBnMFMtNzM3WTdCVG5CX0hkWT0xRTUxNzc4Qw==";

	@BeforeMethod
	public void setUp() {
		baseURI = "https://sahibgill.atlassian.net";
	}

	@Test
	public void getEvents() {
		given().header("Authorization", authorization)
		.when().get("/rest/api/3/events")
		.then().log().all();
	}
	
	@Test(priority = 0)
	public void getCreateIssueMetaData() {
		Response response = given().header("Authorization",authorization)
		.when().get("/rest/api/3/issue/createmeta");
		projectId = response.getBody().jsonPath().get("projects[0].id");
		storyMetaId = response.getBody().jsonPath().get("projects[0].issuetypes[0].id");
		System.out.println(projectId);
		System.out.println(storyMetaId);
	}
	
	@Test(priority = 1)
	public void createIssue() {
		String requestBodyForCreateIssue = "{\r\n"
				+ "  \"fields\": {\r\n"
				+ "    \"assignee\": {\r\n"
				+ "      \"id\": \"5a1bd4931121d32de39ec7cb\"\r\n"
				+ "    },\r\n"
				+ "    \"issuetype\": {\r\n"
				+ "      \"id\": \""+storyMetaId+"\"\r\n"
				+ "    },\r\n"
				+ "    \"project\": {\r\n"
				+ "      \"id\": \""+projectId+"\"\r\n"
				+ "    },\r\n"
				+ "    \"reporter\": {\r\n"
				+ "      \"id\": \"5a1bd4931121d32de39ec7cb\"\r\n"
				+ "    },\r\n"
				+ "    \"summary\": \"Issue created through Automation\"\r\n"
				+ "  }\r\n"
				+ "}";
		given().header("Authorization",authorization).header("Content-Type", "application/json").body(requestBodyForCreateIssue)
		.when().post("/rest/api/3/issue")
		.then().log().all();
		
		
	}

}
