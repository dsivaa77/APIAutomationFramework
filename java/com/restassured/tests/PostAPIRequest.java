package com.restassured.tests;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.restassured.utils.BaseTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PostAPIRequest extends BaseTest{

	@Test
	public void createBooking() {
		
	//	RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();     //single line statement to print the logs when validation fails and we are declared in basetest class
		
		//prepare request body
		JSONObject booking = new JSONObject();
		JSONObject bookingDates = new JSONObject();
		
		booking.put("firstname", "api testing");
		booking.put("lastname", "tutorial");
		booking.put("totalprice", 1000);	
		booking.put("depositpaid", true);
		booking.put("additionalneeds", "breakfast");
		booking.put("bookingdates", bookingDates);
		//bookingDates is having some set of data
		bookingDates.put("checkin", "2024-03-27");
		bookingDates.put("checkout", "2024-03-30");
		
		Response response=
		RestAssured
		      
		        .given()
		           .contentType(ContentType.JSON)
		           .body(booking.toString())
		           .baseUri("https://restful-booker.herokuapp.com/booking")
		      //     .log().body()           //will get only body details
		      //     .log().headers()        //will get headers details
		      //     .log().cookies()        //will get cookies
		      //     .log().uri()            //will get uri
		      //     .log().all()            //will get all details 
		           
		        .when()
		           .post()
		           
		        .then()
		           .assertThat()
		       //    .log().all()
		       //    .log().ifValidationFails()     //Only validation fails it will log the details
		           .statusCode(200)
		           .body("booking.firstname", Matchers.equalTo("api testing"))
		           .body("booking.totalprice", Matchers.equalTo(1000))
		       //    .body("booking.bookingDates.checkin", Matchers.equalTo("2024-03-27"));   //getting exception in date
		         .extract()
		            .response();
		
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
		            
		              
		              
		           
	}
}
