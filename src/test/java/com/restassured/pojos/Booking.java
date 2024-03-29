package com.restassured.pojos;

public class Booking {

	private String firstname;
	private String lastname;
	private String additionalneeds;
	private int totalprice;
	private boolean depositpaid;
	private BookingDates bookingdates;
	
	public Booking() {                                                 //Default Constructor
		
	}
	
	public Booking(String fname, String lname, String aneeds, int tprice, boolean deposit, BookingDates bdates) {        //parameterized constructor
		
		setFirstname(fname);
		setLastname(lname);
		setAdditionalneeds(aneeds);
		setTotalprice(tprice);
		setDepositpaid(deposit);
		setBookingdates(bdates);
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getAdditionalneeds() {
		return additionalneeds;
	}
	public void setAdditionalneeds(String additionalneeds) {
		this.additionalneeds = additionalneeds;
	}
	public int getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}
	public BookingDates getBookingdates() {
		return bookingdates;
	}
	public void setBookingdates(BookingDates bookingdates) {
		this.bookingdates = bookingdates;
	}
	public void setDepositpaid(boolean depositpaid) {
		this.depositpaid=depositpaid;
	}
	public boolean getDepositpaid() {
		return depositpaid;
	}
	
}
