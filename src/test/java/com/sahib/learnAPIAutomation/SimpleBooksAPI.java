package com.sahib.learnAPIAutomation;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SimpleBooksAPI {
	
	String accesToken = "d1def025bc38d4b9ea7b18d57d9960faa4bf7bc6e4c6aec6d7190d8360b2ca77";

	@BeforeMethod
	public void setUp() {
		baseURI = "https://simple-books-api.glitch.me";
	}

	@Test
	public void getListOfBooks() {
		given().params("type", "fiction")
		.when().get("/books")
		.then().statusCode(200).log().all();
	}
	
	@Test
	public void getASingleBook() {
		
		given().pathParam("bookId", 4)
		.when().get("/books/{bookId}")
		.then().assertThat().statusCode(200)
		.and().log().all()
		.and().body("name",equalTo("The Midnight Library"))
		.and().assertThat().body("id",notNullValue())
		.and().assertThat().body("available", equalTo(true));
	}
	
	@Test
	public void submitAnOrder() {
		String requestBody = "{\r\n"
				+ "  \"bookId\": 10,\r\n"
				+ "  \"customerName\": \"Sahib\"\r\n"
				+ "}";
		given().header("Authorization", "Bearer " +accesToken)
		.body(requestBody)
		.when().post("/orders")
		.then().log().all();
		
	}
	
	public void apiAuthentication() {
		given().when().post("/api-clients/")
		.then().log().all();
	}

}
