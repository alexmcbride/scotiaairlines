/* 
 * Author: Alex McBride
 * Date: 03/11/2016
 * Lecturer: James Hood
 * Object Orientated Programming Assessment
 */

package scotiaairlines.models;

public class BusinessTraveller extends Passenger {
	// fields
	private String companyName;
	private String passengerInfo;
	
	// getters
	public String getCompanyName() {
		return companyName;
	}
	
	public String getPassengerInfo() {
		return passengerInfo;
	}

	// setters
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public void setPassengerInfo(String passengerInfo) {
		this.passengerInfo = passengerInfo;
	}
	
	// constructors
	public BusinessTraveller(){
		super(0.75f);
		companyName = "no company name";
	}
	
	public BusinessTraveller(String passengerName, String passengerInfo) {
		this();
		this.passengerName = passengerName;
		this.passengerInfo = passengerInfo;
	}
}
