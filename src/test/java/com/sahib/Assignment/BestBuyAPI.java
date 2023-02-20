package com.sahib.Assignment;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class BestBuyAPI {
	
	private int productId = 0;
	
	@BeforeMethod
	public void setUp() {
		
		baseURI = "http://localhost:3030";
	}
	
	@Test(priority = 0)
	public void getProducts() {
		Response response =  given().
		when().get("/products");
		response.then().assertThat().statusCode(200);
		productId = response.getBody().jsonPath().get("data[0].id");
		System.out.println(productId);
	}
	
	@Test(priority = 1)
	public void getProductById() {
		given().pathParam("id", productId)
		.when().get("/products/{id}")
		.then().log().all()
		.and().assertThat().statusCode(200)
		.and().body("name",equalTo("Duracell - AAA Batteries (4-Pack)"))
		.and().assertThat().body("id",notNullValue())
		.and().body("type", equalTo("HardGood"))
		.and().body("manufacturer", equalTo("Duracell"));
	}
	
	@Test(priority = 2)
	public void deleteProductById() {
		given().pathParam("id", productId)
		.when().delete("/products/{id}")
		.then().log().all()
		.and().assertThat().statusCode(200);
		
	}

}
