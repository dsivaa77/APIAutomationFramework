package com.restassured.tests;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.restassured.listener.RestAssuredListener;
import com.restassured.pojos.Booking;
import com.restassured.pojos.BookingDates;
import com.restassured.utils.FileNameConstants;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;

public class DatadrivenapitestingusingJson {

	@Test(dataProvider="getTestData")
	public void DataDrivenUsingJson(LinkedHashMap<String, String> testdata) throws JsonProcessingException {
			
		BookingDates bookingdates = new BookingDates("2024-03-27","2024-03-29");
		Booking bookings = new Booking(testdata.get("firstname"),testdata.get("lastname"),"Do the job",80000,true,bookingdates); 
		
		ObjectMapper objectMapper = new ObjectMapper();                 //need to add jackson databind maven dependancy
		
			String requestbody;
			
				requestbody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bookings);
			
			Response response =
					
			RestAssured 
			    .given()
			  //      .filter(new RestAssuredListener())
			        .contentType(ContentType.JSON)
			        .body(requestbody)
			        .baseUri("https://restful-booker.herokuapp.com/booking")
			    
			    .when()
			       .post()
			    
			    .then()
			       .assertThat()
			       .statusCode(200)
			       .log().all()
			    .extract()
			       .response();
			 int bookingID = response.path("bookingid");     		
		
	}
	
	@DataProvider(name="getTestData")
	public Object[] getTestDataUsingJson() {
		
		Object[] obj= null;
		
		try {
			
			String jsonTestData = FileUtils.readFileToString(new File(FileNameConstants.JSON_TEST_DATA),"UTF-8");
			JSONArray jsonArray = JsonPath.read(jsonTestData, "$");
			
			obj = new Object[jsonArray.size()];
			
			for(int i=0; i<jsonArray.size(); i++) {
				obj[i]=jsonArray.get(i);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return obj;
		
	}
	
	
	
}
