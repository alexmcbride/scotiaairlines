/* 
 * Author: Alex McBride
 * Date: 03/11/2016
 * Lecturer: James Hood
 * Object Orientated Programming Assessment
 */

package scotiaairlines.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Airline {
	// fields
	private HashMap<String, Flight> flights;
	
	// getters
	public HashMap<String, Flight> getFlights() {
		return flights;
	}
	
	public Seat getSeat(String flightNo, String seatNo) {
		if (flights.containsKey(flightNo)) {
			Flight flight = flights.get(flightNo);
	
			Map<String, Seat> seats = flight.getSeats();
			if (seats.containsKey(seatNo)) {
				return seats.get(seatNo);
			}
			
			Seat seat = new Seat(seatNo);
			seats.put(seatNo, seat);
			return seat;
		}
	
		return null;
	}
	
	public Flight getFlight(String flightId) {
		return flights.get(flightId);
	}
	
	// constructors
	public Airline() {
		flights = new HashMap<>();
	}
	
	// methods
	public void addFlight(Flight flight) {
		if (!flights.containsKey(flight.getFlightNumber())) {
			flights.put(flight.getFlightNumber(), flight);
		}
	}
	
	private static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		return DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\alexm\\Documents\\Eclipse Workspace\\ScotiaAirlines\\Airline.accdb");
	}
	
	public void loadFromDb() throws ClassNotFoundException, SQLException  {
		loadFlightsFromDb();
		loadSeatsFromDb();
		loadPassengersFromDb();
	}
	
	private void loadFlightsFromDb() throws ClassNotFoundException, SQLException {	
		// Load flights from DB.
		try (Connection conn = getConnection()) {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT flightId, departure, arrival, rows, columns FROM Flight");
			
			while (result.next()) {
				String flightNo = result.getString(1);
				String departure = result.getString(2);
				String arrival = result.getString(3);
				int rows = result.getInt(4);
				int columns = result.getInt(5);
				
				Flight flight = new Flight(columns, rows);
				flight.setFlightDetails(flightNo, departure, arrival);
				
				flights.put(flightNo, flight);
			}
		}
	}
	
	private void loadSeatsFromDb() throws ClassNotFoundException, SQLException {	
		// Load seats
		try (Connection conn = getConnection()) {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT seatNo, status, takings, flightId FROM Seat");
			
			while (result.next()) {
				String seatNo = result.getString(1);
				int status = result.getInt(2);
				String flightId = result.getString(4);
				
				Seat seat = new Seat(seatNo);
				switch(status) {
				case 2:
					seat.reserveSeat(null);
					break;
				case 3:
					seat.bookSeat(null);
					break;
				}
				
				Flight flight = flights.get(flightId);
				flight.getSeats().put(seatNo, seat);
			}
		}
	}
	
	private void loadPassengersFromDb() throws ClassNotFoundException, SQLException {	
		// Load passengers
		try (Connection conn = getConnection()) {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT seatNumber, passengerName, type, information, flightId FROM Passenger");
			
			while (result.next()) {
				String seatNo = result.getString(1);
				String name = result.getString(2);
				String type = result.getString(3);
				String info = result.getString(4);
				String flightId = result.getString(5);

				// Get flight and seat
				Flight flight = flights.get(flightId);
				Seat seat = flight.getSeats().get(seatNo);
				
				switch (type) {
				case "I":
					IslandResident i = new IslandResident(name, info);
					i.setIslandOfResidence(info);
					i.setSeatNumber(seatNo);
					seat.setPassenger(i);
					break;
				case "O":
					OrdinaryPassenger o = new OrdinaryPassenger(name, info);
					o.setCurrentPromotion(info.charAt(0));
					o.setSeatNumber(seatNo);
					o.findDiscount();
					seat.setPassenger(o);
					break;
				case "B":
					BusinessTraveller b = new BusinessTraveller(name, info);
					b.setCompanyName(info);
					b.setSeatNumber(seatNo);
					seat.setPassenger(b);
					break;
				}
			}
		}
	}
	
	public void saveToDb() throws ClassNotFoundException, SQLException {
		clearFlightsFromDb();
		saveFlightsToDb();
	}
	
	private void saveFlightsToDb() throws ClassNotFoundException, SQLException {		
		try (Connection conn = getConnection()) {
			for (Flight flight : flights.values()) {
				// Save flight info to DB.
				String sql = "INSERT INTO Flight (flightId, departure, arrival, rows, columns) VALUES (?, ?, ?, ?, ?)";
				try (PreparedStatement statement = conn.prepareStatement(sql)) {
					statement.setString(1, flight.getFlightNumber());
					statement.setString(2, flight.getDeparture());
					statement.setString(3, flight.getArrival());
					statement.setInt(4, flight.getRows());
					statement.setInt(5, flight.getColumns());
					statement.executeUpdate();
				}
				
				// Save seats and passengers
				for (Seat seat : flight.getSeats().values()) {
					// Save seat
					sql = "INSERT INTO Seat (seatNo, status, takings, flightId) VALUES (?, ?, ?, ?)";
					try (PreparedStatement statement = conn.prepareStatement(sql)) {
						statement.setString(1, seat.getSeatNumber());
						statement.setInt(2, getStatusNum(seat.getStatus()));
						statement.setInt(3, (int)seat.getSeatTakings());
						statement.setString(4, flight.getFlightNumber());
						statement.executeUpdate();
					}
					
					// Save passenger (if there is one in this seat)
					Passenger passenger = seat.getPassenger();
					if (passenger != null) {
						sql = "INSERT INTO Passenger (seatNumber, passengerName, type, information, flightId) VALUES (?, ?, ?, ?, ?)";
						try (PreparedStatement statement = conn.prepareStatement(sql)) {
							statement.setString(1, seat.getSeatNumber());
							statement.setString(2, passenger.getPassengerName());
	
							// Varies for different types of passenger
							if (passenger instanceof IslandResident) {
								statement.setString(3, "I");
								statement.setString(4, ((IslandResident)passenger).getIslandOfResidence());
							}
							else if (passenger instanceof OrdinaryPassenger) {
								statement.setString(3, "O");
								statement.setString(4, String.valueOf(((OrdinaryPassenger)passenger).getCurrentPromotion()));
							}
							else if (passenger instanceof BusinessTraveller) {
								statement.setString(3, "B");
								statement.setString(4, ((BusinessTraveller)passenger).getCompanyName());
							}
							statement.setString(5, flight.getFlightNumber());
							
							statement.executeUpdate();
						}
					}
				}
			}
		}
	}
	
	private static int getStatusNum(SeatStatus status) {
		switch (status) {
		case FREE:
			return 1;
		case RESERVED:
			return 2;
		case BOOKED:
			return 3;
		}
		return 0;
	}
	
	private void clearFlightsFromDb() throws ClassNotFoundException, SQLException {
		try (Connection conn = getConnection()) {
			conn.createStatement().execute("DELETE FROM Flight");	
			conn.createStatement().execute("DELETE FROM Seat");
			conn.createStatement().execute("DELETE FROM Passenger");
		}
	}
}
