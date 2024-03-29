package com.restassured.tests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.restassured.utils.FileNameConstants;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PatchAPIRequest {

	@Test
	public void patchAPIRequest() {
		
		try {
			String postAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstants.POST_API_REQUEST_BODY), "UTF-8");
			
			String tokenAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstants.TOKEN_API_REQUEST_BODY), "UTF-8");
			
			String putAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstants.PUT_API_REQUEST_BODY), "UTF-8");
			
			String patchAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstants.PATCH_API_REQUEST_BODY), "UTF-8");
			
			
		//	System.out.println(postAPIRequestBody);           //know the file body
			
			//POST api call
			
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
		       
		       //GET api call
		       RestAssured
		              .given()
		                 .contentType(ContentType.JSON)
		                 .pathParam("bookingid", bookingID)
		                 .baseUri("https://restful-booker.herokuapp.com/booking")
		              .when()
		                 .get("{bookingid}")
		                 
		              .then()
		                 .assertThat()
		                 .statusCode(200);
		            //     .body("booking.lastname", Matchers.equalTo("tutorial"))
		            //     .log().all();
		       
		       //Get the request for token
		       
		       Response tokenAPIResponse=
		       RestAssured
		            .given()
		               .contentType(ContentType.JSON)
		               .body(tokenAPIRequestBody)
		               .baseUri("https://restful-booker.herokuapp.com/auth")
		               
		            .when()
		               .post()
		            
		            .then()
		               .assertThat()
		               .statusCode(200)
		               
		             .extract()
		                .response();
		       
		       String token = tokenAPIResponse.path("token");  
		       System.out.println("The token is "+token);
		       
		 //PUT request for update the details
		       
		       RestAssured
		           .given()
		              .contentType(ContentType.JSON)
		              .body(putAPIRequestBody)
		              .pathParam("bookingid", bookingID)
		              .header("Cookie", "token="+token)
		              .baseUri("https://restful-booker.herokuapp.com/booking")
		              
		           .when()
		              .put("{bookingid}")
		           
		           .then()
		              .assertThat()
		           //   .log().all()
		              .statusCode(200)
		              .body("firstname", Matchers.equalTo("Specflow"));
		       
		       
		       
		     //PATCH request for partial update the details  
		       
		          RestAssured
		               .given()
		                   .contentType(ContentType.JSON)
		                   .pathParam("bookingid", bookingID)
		                   .header("Cookie", "token="+token)
		                   .body(patchAPIRequestBody)
		                   .baseUri("https://restful-booker.herokuapp.com/booking")
		               
		               .when()
		                    .patch("{bookingid}")
		               
		               .then()
		                   .assertThat()
		                   .statusCode(200)
		                   .body("firstname", Matchers.equalTo("Testers Talk"))
		                 //  .log().all()
		                   .log().body();
		               
		               
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
