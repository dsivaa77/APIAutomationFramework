package com.restassured.tests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restassured.pojos.Booking;
import com.restassured.pojos.BookingDates;
import com.restassured.utils.FileNameConstants;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class PostAPIRequestUsingPojos {

	@Test
	public void postAPIReqeust() {
		
		try {
			String jsonSchema = FileUtils.readFileToString(new File(FileNameConstants.JSON_SCHEMA),"UTF-8");
			
		BookingDates bookingdates = new BookingDates("2024-03-27","2024-03-29");
		Booking bookings = new Booking("Shiva","Dodda","Do the job",80000,true,bookingdates);
		
		//Serialization : converts the java class object to json object 
		//De-serialization : converts the json object to java class object 
		
		ObjectMapper objectMapper = new ObjectMapper();                 //need to add jackson databind maven dependancy
		
		
			//Serialization :
			String requestbody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bookings);
			
			System.out.println(requestbody);
			
			//De-Serialization
			Booking bookingDetails = objectMapper.readValue(requestbody, Booking.class);
			System.out.println(bookingDetails.getFirstname());
			System.out.println(bookingDetails.getLastname());
			System.out.println(bookingDetails.getTotalprice());
			System.out.println(bookingDetails.getDepositpaid());
			System.out.println(bookingDetails.getBookingdates().getCheckin());
			System.out.println(bookingDetails.getBookingdates().getCheckout());
			
			Response response =
					
			RestAssured 
			    .given()
			        .contentType(ContentType.JSON)
			        .body(requestbody)
			        .baseUri("https://restful-booker.herokuapp.com/booking")
			    
			    .when()
			       .post()
			    
			    .then()
			       .assertThat()
			       .statusCode(200)
			    .extract()
			       .response();
			 int bookingID = response.path("bookingid");
			 
		//	 System.out.println(jsonSchema);                    //It will show JsonSchema 
			 
			 RestAssured
			      .given()
			        .contentType(ContentType.JSON)
			        .baseUri("https://restful-booker.herokuapp.com/booking")
			        
			      .when()
			        .get("/{bookingID}",bookingID)
			      
			      .then()
			        .assertThat()
			        .statusCode(200)
			        .body(JsonSchemaValidator.matchesJsonSchema(jsonSchema));      //JsonSchema validation
			   //     .log().all();
			 
			      
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
