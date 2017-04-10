/* 
 * Author: Alex McBride
 * Date: 03/11/2016
 * Lecturer: James Hood
 * Object Orientated Programming Assessment
 */

package scotiaairlines.models;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Flight {
	// fields
	private String flightNumber;
	private String departure;
	private String arrival;
	private Date date;
	private int columns;
	private int rows;
	private Map<String, Seat> seats;
	private FlightStatus status;
	
	// getters
	public String getFlightNumber() {
		return flightNumber;
	}
	
	public String getDeparture() {
		return departure;
	}
	
	public String getArrival() {
		return arrival;
	}
	
	public Date getDate() {
		return date;
	}
	
	public int getFreeSeats() {
		int count = 0;
		for (Seat seat : seats.values()) {
			if (seat.getStatus() == SeatStatus.FREE) {
				count++;	
			}
		}
		return count;
	}
	
	public int getBookedSeats() {
		int count = 0;
		for (Seat seat : seats.values()) {
			if (seat.getStatus() == SeatStatus.BOOKED) {
				count++;	
			}
		}
		return count;
	}
	
	public int getReservedSeats() {
		int count = 0;
		for (Seat seat : seats.values()) {
			if (seat.getStatus() == SeatStatus.RESERVED) {
				count++;	
			}
		}
		return count;
	}
	
	public boolean isFull() {
		return getFreeSeats() == 0;
	}
	
	public boolean isCheckingIn() {
		return status == FlightStatus.CHECKING_IN;
	}
	
	public boolean isClosed() {
		return status == FlightStatus.CLOSED;
	}
	
	public boolean isBoarding() {
		return status == FlightStatus.BOARDING;
	}
	
	public Map<String, Seat> getSeats() {
		return seats;
	}
	
	public float getTotalFlightTakings() {
		float takings = 0;
		for (Seat seat : seats.values()) {
			takings += seat.getSeatTakings();
		}
		return takings;
	}
	
	public int getColumns() {
		return columns;
	}

	public int getRows() {
		return rows;
	}
	
	public String getStatusMessage() {
		switch (status) {
		case SEATS_AVAILABLE:
			return "Seats Available";
		case CHECKING_IN:
			return "Checking-In";
		case BOARDING:
			return "Boarding";
		case CLOSED:
			return "Flight Closed";
		}
		return "None";
	}

	// setters
	public void setFlightDetails(String flightNumber, String departure, String arrival) {
		this.flightNumber = flightNumber;
		this.departure = departure;
		this.arrival = arrival;
	}
	
	public void setColumns(int columns) {
		this.columns = columns;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public void setFlightStatus(FlightStatus statusCode) {
		status = statusCode;
	}
	
	// constructors
	public Flight(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
		this.seats = new HashMap<>();
		this.flightNumber = "no flight number";
		this.departure = "no departure";
		this.arrival = "no arrival";
		this.date = new Date();
		this.status = FlightStatus.SEATS_AVAILABLE;
	}
	
	// methods
	public String displayFlightInfo() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String takings = formatter.format(getTotalFlightTakings());
		
		String output = "Flight Number: " + flightNumber + "<br />";
		output += "Arrival Airport: " + arrival + "<br />";
		output += "Free Seats: " + getFreeSeats() + "<br />";
		output += "Reserved Seats: " + getReservedSeats() + "<br />";
		output += "Booked Seats: " + getBookedSeats() + "<br />";
		output += "Status: " + getStatusMessage() + "<br />";
		output += "Total Flight Takings: " + takings + "<br />";
		return output;
	}

	public Seat getSeat(String seatNumber) {
		return seats.get(seatNumber);
	}
	
	public void cancelSeat(Seat seat) {
		seat.cancelSeat();
	}
	
	public void cancelSeat(Passenger passenger) {
		Seat seat = getSeat(passenger.getSeatNumber());
		seat.cancelSeat();
	}
	
	public void bookSeat(Passenger passenger) {
		Seat seat = getSeat(passenger.getSeatNumber());
		seat.bookSeat(passenger);
	}
	
	public void reserveSeat(Passenger passenger) {
		Seat seat = getSeat(passenger.getSeatNumber());
		seat.reserveSeat(passenger);
	}	

	public void addSeat(Seat seat) {
		seats.put(seat.getSeatNumber(), seat);
	}
	
	public boolean isValidSeatNo(String seatNo) {
		String number = "";
		String letter = "";
		int element = -1;
		boolean shouldLeaveLoop = false;
		
		for (char c : seatNo.toCharArray()) {
			if (!shouldLeaveLoop) {
				try {
					String character = String.valueOf(c);
					Integer.parseInt(character);
					number += c;
					element++;
				}
				catch (Exception e) {
					shouldLeaveLoop = true;
				}
			}
		}
		
		boolean lastPartIsCharacter = true;
		letter = seatNo.substring(element + 1);
		if (letter.length() == 1) {
			char letterChar = letter.charAt(0);
			if (!Character.isLetter(letterChar)) {
				lastPartIsCharacter = false;
			}
		}
		else {
			lastPartIsCharacter = false;
		}
		
		try {
			if (Integer.parseInt(number) > columns || number.equals("") || letter.length() != 1 || !lastPartIsCharacter) {
				return false;
			}
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
}
