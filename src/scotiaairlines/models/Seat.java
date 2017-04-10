/* 
 * Author: Alex McBride
 * Date: 03/11/2016
 * Lecturer: James Hood
 * Object Orientated Programming Assessment
 */

package scotiaairlines.models;

import java.text.NumberFormat;

public class Seat {
	// fields
	private String seatNumber;
	private float seatPrice;
	private Passenger passenger;
	private SeatStatus status;

	// getters
	public String getSeatNumber() {
		return seatNumber;
	}
	
	public float getSeatPrice() {
		return seatPrice;
	}
	
	public float getSeatTakings() {
		if (passenger != null && isBooked()) {
			return seatPrice * passenger.getDiscountAmount();
		}
		return 0;
	}
	
	public Passenger getPassenger() {
		return passenger;
	}
	
	public SeatStatus getStatus() {
		return status;
	}
	
	// setters
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	
	public void setSeatPrice(float seatPrice) {
		this.seatPrice = seatPrice;
	}
	
	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	// constructors
	public Seat() {
		this("no seat number");
	}
	
	public Seat(String seatNumber) {
		this.seatNumber = seatNumber; 
		this.seatPrice = 100f;
		this.status = SeatStatus.FREE;
		this.passenger = null;
	}
	
	// methods
	public String displaySeatDetails() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String takings = formatter.format(getSeatTakings());
		String price = formatter.format(seatPrice);
		String status = getStatusText();
		
		String output = "Seat No: " + seatNumber + "<br />"
				+ "Current Status: " + status + "<br />"
				+ "Seat Price: " + price + "<br />"
				+ "Seat Takings: " + takings;
		
		if (passenger != null) {
			output += "<br />Passenger Name: " + passenger.getPassengerName();
		}
		
		return output;
	}
	
	public String getStatusText() {
		switch (status) {
		case FREE:
			return "Free";
		case RESERVED:
			return "Reserved";
		case BOOKED:
			return "Booked";
		}
		return "None";
	}
	
	public boolean isFree() {
		return status == SeatStatus.FREE;
	}
	
	public boolean isReserved() {
		return status == SeatStatus.RESERVED;
	}	
	
	public boolean isBooked() {
		return status == SeatStatus.BOOKED;
	}

	public void cancelSeat() {
		passenger = null;
		status = SeatStatus.FREE;
	}

	public void reserveSeat(Passenger passenger) {
		this.passenger = passenger;
		this.status = SeatStatus.RESERVED;
	}
	
	public void bookSeat(Passenger passenger) {
		this.passenger = passenger;
		this.status = SeatStatus.BOOKED;
	}
	
	public void bookSeat() {
		this.status = SeatStatus.BOOKED;
	}
	
	public boolean passengerHasReserved(String name) {
		return passenger != null && passenger.getPassengerName().equalsIgnoreCase(name);
	}
}
