/* 
 * Author: Alex McBride
 * Date: 03/11/2016
 * Lecturer: James Hood
 * Object Orientated Programming Assessment
 */

package scotiaairlines.models;

public class OrdinaryPassenger extends Passenger {
	// fields
	private char currentPromotion;
	private String passengerInfo;
	
	// getters
	public char getCurrentPromotion() {
		return currentPromotion;
	}
	
	public String getPassengerInfo() {
		return passengerInfo;
	}

	// setters
	public void setCurrentPromotion(char currentPromotion) {
		this.currentPromotion = currentPromotion;
	}
	
	public void setPassengerInfo(String passengerInfo) {
		this.passengerInfo = passengerInfo;
	}
	
	// constructors
	public OrdinaryPassenger() {
		super(1);
	}
	
	public OrdinaryPassenger(String passengerName, String passengerInfo) {
		this();
		this.passengerName = passengerName;
		this.passengerInfo = passengerInfo;
	}
	
	// methods
	public void findDiscount() {
		if (currentPromotion == 'y') {
			discountAmount = 0.95f;
		}
		else {
			discountAmount = 1;
		}
	}
}
