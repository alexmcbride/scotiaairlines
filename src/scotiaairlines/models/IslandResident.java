/* 
 * Author: Alex McBride
 * Date: 03/11/2016
 * Lecturer: James Hood
 * Object Orientated Programming Assessment
 */

package scotiaairlines.models;

public class IslandResident extends Passenger {
	// fields
	private String islandOfResidence;
	private String passengerInfo;
	
	// getters
	public String getIslandOfResidence() {
		return islandOfResidence;
	}
	
	public String getPassengerInfo() {
		return passengerInfo;
	}
	
	// setters
	public void setIslandOfResidence(String islandOfResidence) {
		this.islandOfResidence = islandOfResidence;
	}
	
	public void setPassengerInfo(String passengerInfo) {
		this.passengerInfo = passengerInfo;
	}
	
	// constructors
	public IslandResident() {
		super(0.9f);
		islandOfResidence = "no island of residence";
	}
	
	public IslandResident(String passengerName, String passengerInfo) {
		this();
		this.passengerName = passengerName;
		this.setPassengerInfo(passengerInfo);
	}
}
