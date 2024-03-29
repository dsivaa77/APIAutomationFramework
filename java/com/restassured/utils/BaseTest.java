package com.restassured.utils;

import org.testng.annotations.BeforeMethod;

import io.restassured.RestAssured;

public class BaseTest {

	@BeforeMethod
	public void beforemethod() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();     //single line statement to print the logs when validation fails
	}
}
