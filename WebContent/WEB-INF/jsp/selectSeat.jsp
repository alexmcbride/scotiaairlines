<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scotia Airlines - Select Seat</title>
</head>
<body>
	<h1>Scotia Airlines - Select Seat Number</h1>
	
	<p>Please select a seat:</p>

	<!-- Loop through each seat and output a form -->
	<c:forEach var="seat" items="${flight.seats}">
		<form action="selectSeat" method="POST">
			<input type="submit" name="selectedSeat" value="ID: ${seat.value.seatNumber}, Status: ${seat.value.statusText}" />
			
			<!-- Store seat number in hidden input -->
			<input type="hidden" name="seatNumber" value="${seat.value.seatNumber}"/>
		</form>
		<br/>
	</c:forEach>
	
	<hr />
			
	<!-- Show add seat only if needed -->
	<c:if test="${showAddSeat}">
		<!-- Adds a new seat to the current flight -->
		<form action="addSeat" method="POST">
			<label for="seatNumber">Seat Number: </label>
			<input type="text" name="seatNumber"/>
			<input type="submit" value="Add Seat"/>
		</form>
		<hr />
		<br />
	</c:if>

	<input type="button" value="Back to Booking Menu" onclick="location.href='bookingMenu'"/>
</body>
</html>