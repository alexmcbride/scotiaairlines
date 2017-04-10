/* 
 * Author: Alex McBride
 * Date: 03/11/2016
 * Lecturer: James Hood
 * Object Orientated Programming Assessment
 */

package scotiaairlines.models;

public abstract class Passenger {
	// fields
	protected float discountAmount;
	protected String passengerName;
	private String seatNumber;
	
	// getters
	public float getDiscountAmount() {
		return discountAmount;
	}

	public String getPassengerName() {
		return passengerName;
	}

	// setters
	public void setDiscountAmount(float discountAmount) {
		this.discountAmount = discountAmount;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	
	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	// constructors
	public Passenger() {
		this(1f);
	}
	
	public Passenger(float discountAmount) {
		this.discountAmount = discountAmount;
		this.passengerName = "no passenger name";
	}
}
