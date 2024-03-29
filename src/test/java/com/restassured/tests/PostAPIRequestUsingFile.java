package com.restassured.tests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.restassured.utils.BaseTest;
import com.restassured.utils.FileNameConstants;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PostAPIRequestUsingFile extends BaseTest {

	@Test
	public void postAPIRequest() {
		
		try {
			String postAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstants.POST_API_REQUEST_BODY), "UTF-8");
			
		//	System.out.println(postAPIRequestBody);           //know the file body
			Response response =
			RestAssured
			    .given()
			       .contentType(ContentType.JSON)
			       .body(postAPIRequestBody)
			       .baseUri("https://restful-booker.herokuapp.com/booking")
			    .when()
			       .post()
			    .then()
			       .assertThat()
			       .statusCode(200)
			       .body("booking.firstname", Matchers.equalTo("api testing"))
			    .extract()
			       .response();
			
	//   JSONArray jsonarray = JsonPath.read(response.body().toString(),"$.booking..firstname");   //getting error --need to check
			int bookingID = response.path("bookingid"); 
		       System.out.println("booking id is " + bookingID);
		       
		       RestAssured
		              .given()
		                 .contentType(ContentType.JSON)
		                 .pathParam("bookingid", bookingID)
		                 .baseUri("https://restful-booker.herokuapp.com/booking")
		              .when()
		                 .get("{bookingid}")
		                 
		              .then()
		                 .assertThat()
		                 .statusCode(200)
		            //     .body("booking.lastname", Matchers.equalTo("tutorial"))
		                 .log().all();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}