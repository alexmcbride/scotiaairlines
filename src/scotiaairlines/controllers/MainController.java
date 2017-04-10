/* 
 * Author: Alex McBride
 * Date: 09/03/2016
 * Lecturer: James Hood
 * Object Orientated Programming Assessment
 */

package scotiaairlines.controllers;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import scotiaairlines.models.Airline;
import scotiaairlines.models.BusinessTraveller;
import scotiaairlines.models.Flight;
import scotiaairlines.models.FlightStatus;
import scotiaairlines.models.IslandResident;
import scotiaairlines.models.OrdinaryPassenger;
import scotiaairlines.models.Passenger;
import scotiaairlines.models.Seat;

@Controller
public class MainController {
	private Airline getAirline(HttpServletRequest request) {
		// Helper method for getting airline from session.
		return (Airline)request.getSession().getAttribute("airline");
	}

	private Flight getFlight(HttpServletRequest request) {
		// Ditto for current flight.
		return (Flight)request.getSession().getAttribute("flight");
	}
	
	private Seat getSeat(HttpServletRequest request) {
		// Ditto seat
		return (Seat)request.getSession().getAttribute("seat");
	}
	
	private ModelAndView RedirectToAction(String action) {
		// Redirect to the specified controller action.
		return new ModelAndView("redirect: /ScotiaAirlines/" + action);
	}
	
	@RequestMapping(value="/mainMenu", method=RequestMethod.GET) 
	public String getMainMenu(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		// Load airline from DB if it doesn't exist.
		Airline airline = getAirline(request);
		if (airline == null) {
			airline = new Airline();
			airline.loadFromDb();
			request.getSession().setAttribute("airline", airline);
		}
		return "mainMenu";
	}
	
	@RequestMapping(value="/mainMenu", method=RequestMethod.POST) 
	public ModelAndView postMainMenu(HttpServletRequest request, @RequestParam Map<String, String> params) {	
		String menuChoice = params.get("menuChoice");
		
		switch (menuChoice) {
		case "Booking Menu":
			request.getSession().setAttribute("menuChoice", "booking");
			break;
		case "Display Flight Seats":
			request.getSession().setAttribute("menuChoice", "displaySeats");
			break;
		case "Display Flight Details":
			request.getSession().setAttribute("menuChoice", "displayFlight");
			break;
		case "Change Flight Status":
			// Called from flightAdmin.jsp
			request.getSession().setAttribute("menuChoice", "changeFlightStatus");
			break;
		}
		
		return RedirectToAction("selectFlight");
	}
	
	@RequestMapping(value="/flightAdmin", method=RequestMethod.GET) 
	public String getFlightAdmin(HttpServletRequest request) {
		return "flightAdmin";
	}
	
	@RequestMapping(value="/flightAdmin", method=RequestMethod.POST) 
	public String postFlightAdmin(HttpServletRequest request, @RequestParam Map<String, String> params) {
		return "flightAdmin";
	}
	
	@RequestMapping(value="/selectFlight", method=RequestMethod.GET) 
	public ModelAndView getSelectFlight(HttpServletRequest request) {	
		// Display list of flights to select.
		Airline airline = getAirline(request);
		return new ModelAndView("selectFlight", "airline", airline);
	}
	
	@RequestMapping(value="/selectFlight", method=RequestMethod.POST) 
	public ModelAndView postSelectFlight(HttpServletRequest request, @RequestParam Map<String, String> params) {	
		// Get selected flight and assign to session.
		String flightId = params.get("flightId");
		Flight flight = getAirline(request).getFlight(flightId);
		request.getSession().setAttribute("flight", flight);
		
		// Get mainMenu choice (or sometimes coming back from Booking Menu)
		String menuChoice = (String)request.getSession().getAttribute("menuChoice");
		switch (menuChoice) {
		case "booking":
		case "cancel":
		case "reserve":
		case "book":
			return RedirectToAction("bookingMenu");			
		case "changeFlightStatus":
			return RedirectToAction("changeFlightStatus");	
		case "displaySeats":
			return RedirectToAction("selectSeat");	
		case "displayFlight":
			return RedirectToAction("flightDetails");		
		}
		
		return null;
	}
	
	@RequestMapping(value="/addNewFlight", method=RequestMethod.GET) 
	public String getAddNewFlight(HttpServletRequest request) {		
		return "addNewFlight";
	}
	
	@RequestMapping(value="/addNewFlight", method=RequestMethod.POST) 
	public ModelAndView postAddNewFlight(HttpServletRequest request, @RequestParam Map<String, String> params) throws ClassNotFoundException, SQLException {	
		// Get stuff from form
		String flightId = params.get("flightId");
		String departure = params.get("departure");
		String arrival = params.get("arrival");
		int rows = Integer.parseInt(params.get("rows"));
		int columns = Integer.parseInt(params.get("columns"));
		
		// Create new flight.
		Flight flight = new Flight(columns, rows);
		flight.setFlightDetails(flightId, departure, arrival);
		
		// Add to airline
		Airline airline = getAirline(request);
		airline.addFlight(flight);
		airline.saveToDb();
		
		// Confirmation message
		String message = "Flight '"+flightId+"' added to airline";
		return new ModelAndView("addNewFlight", "message", message);
	}
	
	@RequestMapping(value="/bookingMenu", method=RequestMethod.GET) 
	public String getBookingMenu(HttpServletRequest request) {		
		return "bookingMenu";
	}
	
	@RequestMapping(value="/bookingMenu", method=RequestMethod.POST) 
	public ModelAndView postBookingMenu(HttpServletRequest request, @RequestParam Map<String, String> params) {
		// Get booking menu button submitted and set in session.
		String menuChoice = params.get("menuChoice");
		Flight flight = getFlight(request);
		
		switch (menuChoice) {
		case "Cancel a Reservation Booking":
			// Check if can still cancel seat.
			if (flight.isBoarding() || flight.isClosed()) {
				return new ModelAndView("genericOutput", "message", "Cancellations not available, flight status: " + flight.getStatusMessage());
			}
			request.getSession().setAttribute("menuChoice", "cancel");
			break;
		case "Reserve a Seat":
			// Check if can reserve seat
			if (flight.isBoarding() || flight.isClosed() || flight.isCheckingIn()) {
				return new ModelAndView("genericOutput", "message",  "Reservations not available, flight status: " + flight.getStatusMessage());
			}
			request.getSession().setAttribute("menuChoice", "reserve");
			break;
		case "Book a Seat":
			// Check if can book seat.
			if (flight.isBoarding() || flight.isClosed()) {
				return new ModelAndView("genericOutput", "message",  "Booking not available, flight status: " + flight.getStatusMessage());
			}			
			request.getSession().setAttribute("menuChoice", "book");
			break;
		}
		
		return RedirectToAction("selectSeat");
	}
	
	@RequestMapping(value="/changeFlightStatus", method=RequestMethod.GET) 
	public String getChangeFlightStatus(HttpServletRequest request) {		
		return "changeFlightStatus";
	}
	
	@RequestMapping(value="/changeFlightStatus", method=RequestMethod.POST) 
	public ModelAndView postChangeFlightStatus(HttpServletRequest request, @RequestParam Map<String, String> params) throws ClassNotFoundException, SQLException {
		String menuChoice = params.get("menuChoice");
		Flight flight = getFlight(request);
		
		// Handle menuChoice set in changeFlightStatus menu
		String message = null;
		switch (menuChoice) {
		case "Seats Available":
			flight.setFlightStatus(FlightStatus.SEATS_AVAILABLE);
			break;
		case "Checking In":
			flight.setFlightStatus(FlightStatus.CHECKING_IN);
			break;
		case "Boarding":
			flight.setFlightStatus(FlightStatus.BOARDING);
			break;
		case "Flight Closed":
			flight.setFlightStatus(FlightStatus.CLOSED);
			break;
		}
		
		// If no message set
		if (message == null) {			
			message = "Flight status changed to: " + flight.getStatusMessage();
		}
		
		return new ModelAndView("genericOutput", "message", message);
	}	
	
	@RequestMapping(value="/selectSeat", method=RequestMethod.GET) 
	public ModelAndView getSelectSeat(HttpServletRequest request) {	
		String menuChoice = (String)request.getSession().getAttribute("menuChoice");
		boolean addSeat = menuChoice.equals("reserve") || menuChoice.equals("book");
		
		// Display list of seats on the flight.
		return new ModelAndView("selectSeat", "showAddSeat", addSeat);
	}

	@RequestMapping(value="/selectSeat", method=RequestMethod.POST) 
	public ModelAndView postSelectSeat(HttpServletRequest request, @RequestParam Map<String, String> params) {
		String seatNumber = params.get("seatNumber");
		
		// Get seat from flight and save in session.
		Flight flight = getFlight(request);
		Seat seat = flight.getSeat(seatNumber);
		request.getSession().setAttribute("seat", seat);
	
		// Redirect to correct action for menu choice.
		String menuChoice = (String)request.getSession().getAttribute("menuChoice");
		switch (menuChoice) {
		case "cancel":
			return RedirectToAction("cancelSeat");
		case "reserve":
			return RedirectToAction("reserveSeat");
		case "book":
			return RedirectToAction("bookSeat");
		case "displaySeats":
			return RedirectToAction("seatDetails");
		}
		
		return null;
	}

	@RequestMapping(value="/cancelSeat", method=RequestMethod.GET) 
	public ModelAndView getCancelSeat(HttpServletRequest request) throws ClassNotFoundException, SQLException {	
		Seat seat = getSeat(request);
		Flight flight = getFlight(request);
		
		// Don't update if already no one in this seat
		if (seat.isFree()) {
			return new ModelAndView("genericOutput", "message", "Cancellation Error - the seat '" + seat.getSeatNumber() + "' is empty");
		}
		
		// Set seat to cancelled
		flight.cancelSeat(seat);
		
		// Save DB
		Airline airline = getAirline(request);
		airline.saveToDb();
		
		// Confirmation message.
		String message = "<b>Cancellation Success</b><br/>"; 
		message += "<br/>Seat Number: " + seat.getSeatNumber();
		message += "<br/>Status: cancelled";
		return new ModelAndView("genericOutput", "message", message);
	}
	
	@RequestMapping(value="/reserveSeat", method=RequestMethod.GET) 
	public ModelAndView getReserveSeat(HttpServletRequest request) {	
		Seat seat = getSeat(request);
		
		// If there is already someone in this seat, show a message.
		if (seat.isReserved()) {
			return new ModelAndView("genericOutput", "message", "Reservation Error - the seat '" + seat.getSeatNumber() + "' is already reserved");
		}
		
		if (seat.isBooked()) {
			return new ModelAndView("genericOutput", "message", "Reservation Error - the seat '" + seat.getSeatNumber() + "' is already booked");
		}
		
		return RedirectToAction("addPassenger");
	}
	
	
	@RequestMapping(value="/bookSeat", method=RequestMethod.GET) 
	public ModelAndView getBookSeat(HttpServletRequest request) {	
		Seat seat = getSeat(request);
		
		// If there is already someone in this seat, show a message.
		if (seat.isBooked()) {
			return new ModelAndView("genericOutput", "message", "Booking Error - the seat '" + seat.getSeatNumber() + "' is already booked");
		}
		
		return RedirectToAction("addPassenger");
	}
	
	@RequestMapping(value="/addPassenger", method=RequestMethod.GET) 
	public ModelAndView getAddPassenger(HttpServletRequest request) {		
		// Display form for adding a new passenger.
		Seat seat = getSeat(request);
		return new ModelAndView("addPassenger");
	}
	
	@RequestMapping(value="/addPassenger", method=RequestMethod.POST) 
	public ModelAndView postAddPassenger(HttpServletRequest request, @RequestParam Map<String, String> params) {
		// Get stuff from form.
		String name = params.get("passengerName").trim();
		String type = params.get("passengerType");
		
		Seat seat = getSeat(request);
		
		// If seat is reserved make sure name is the same.
		if (seat.isReserved() && !seat.passengerHasReserved(name)) {
			// Not same person, show error.
			String message = "Booking Error - this reserved seat '" + seat.getSeatNumber() + "' can only be booked by '" + seat.getPassenger().getPassengerName() + "'";
			return new ModelAndView("genericOutput", "message", message);
		}
		
		// Create new passenger of correct type and store in session.
		switch (type) {
		case "O":
			OrdinaryPassenger o = new OrdinaryPassenger(name, null);
			o.setSeatNumber(seat.getSeatNumber());
			request.getSession().setAttribute("passenger", o);
			break;
		case "I":
			IslandResident i = new IslandResident(name, null);
			i.setSeatNumber(seat.getSeatNumber());
			request.getSession().setAttribute("passenger", i);
			break;
		case "B":
			BusinessTraveller b = new BusinessTraveller(name, null);
			b.setSeatNumber(seat.getSeatNumber());
			request.getSession().setAttribute("passenger", b);
			break;
		}
		
		// Redirect to action to get more info for this particular passenger type.
		return RedirectToAction("addInfo");
	}

	@RequestMapping(value="/addInfo", method=RequestMethod.GET) 
	public String getAddInfo(HttpServletRequest request) {	
		Passenger passenger = (Passenger)request.getSession().getAttribute("passenger");
		
		// Return correct view for this passenger type.
		if (passenger instanceof IslandResident) {
			return "addIslandName";
		}
		else if (passenger instanceof OrdinaryPassenger) {
			return "addPromo";
		}
		else if (passenger instanceof BusinessTraveller) {
			return "addCompanyName";
		}
		
		return null;
	}
	
	@RequestMapping(value="/addInfo", method=RequestMethod.POST) 
	public ModelAndView postAddInfo(HttpServletRequest request, @RequestParam Map<String, String> params) throws ClassNotFoundException, SQLException {
		Passenger passenger = (Passenger)request.getSession().getAttribute("passenger");
		
		// Get the form results from the view for the particular passenger type
		if (passenger instanceof IslandResident) {
			String islandName = params.get("islandName");
			IslandResident island = (IslandResident)passenger;
			island.setIslandOfResidence(islandName);
		}
		else if (passenger instanceof OrdinaryPassenger) {
			char promotion = params.get("promotion").charAt(0);
			OrdinaryPassenger ordinary = (OrdinaryPassenger)passenger;
			ordinary.setCurrentPromotion(promotion);
			ordinary.findDiscount();
		}
		else if (passenger instanceof BusinessTraveller) {
			String companyName = params.get("companyName");
			BusinessTraveller business = (BusinessTraveller)passenger;
			business.setCompanyName(companyName);
		}
		
		// Get the type of update we're doing (reserve or book).
		String menuChoice = (String)request.getSession().getAttribute("menuChoice");
		
		// Add passenger to seat and update flight.
		String message = null;
		Flight flight = getFlight(request);
		if (menuChoice.equals("reserve")) {
			flight.reserveSeat(passenger);
			message = "<b>Reservation Made</b><br/>";
			message += "<br/>Seat Number: " + passenger.getSeatNumber();
			message += "<br/>Passenger Name: " + passenger.getPassengerName();
		}
		else if (menuChoice.equals("book")) {
			boolean reserved =  getSeat(request).isReserved(); // Get if was reserved
			
			// Book flight.
			flight.bookSeat(passenger);
			message = "<b>Booking Made</b><br/>";
			message += "<br/>Seat Number: " + passenger.getSeatNumber();
			message += "<br/>Passenger Name: " + passenger.getPassengerName();
			
			// Add reserved status changed note.
			if (reserved) {
				message += "<br/>Status: changed from 'reserved' to 'booked'";
			}
		}
		
		// Save DB
		Airline airline = getAirline(request);
		airline.saveToDb();
		
		return new ModelAndView("genericOutput", "message", message);
	}	


	@RequestMapping(value="/flightDetails", method=RequestMethod.GET) 
	public ModelAndView getFlightDetails(HttpServletRequest request) {	
		// Display details for the flight stored in the session.
		Flight flight = getFlight(request);
		String message = flight.displayFlightInfo();
		return new ModelAndView("genericOutput", "message", message);
	}
	
	@RequestMapping(value="/seatDetails", method=RequestMethod.GET) 
	public ModelAndView getSeatDetails(HttpServletRequest request) {	
		// Display details of the seat in the session.
		Seat seat = getSeat(request);
		String message = seat.displaySeatDetails();
		return new ModelAndView("genericOutput", "message", message);
	}
	
	@RequestMapping(value="/addSeat", method=RequestMethod.POST) 
	public ModelAndView postAddSeat(HttpServletRequest request, @RequestParam Map<String, String> params) throws ClassNotFoundException, SQLException {
		// Get new seat number from form
		String seatNumber = params.get("seatNumber").trim();
		
		// Check if the number is valid
		Flight flight = getFlight(request);
		if (flight.isValidSeatNo(seatNumber)) {
			// Add seat to flight
			Seat seat = new Seat(seatNumber);
			flight.addSeat(seat);
			
			// Save Db
			Airline airline = getAirline(request);
			airline.saveToDb();
			
			// Go back to seat selection screen.
			return RedirectToAction("selectSeat");
		}
		
		// Error
		return new ModelAndView("genericOutput", "message", "Add Seat Error - '" + seatNumber + "' is not a valid seat number");
	}
}








