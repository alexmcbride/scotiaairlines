<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Scotia Airlines - Booking Menu</title>
</head>
<body>
	<h1>Scotia Airlines - Booking Menu</h1>
	
	<p><strong>Flight Number: ${flight.flightNumber}</strong></p>
	
	<form action="bookingMenu" method="POST">
		<input type="submit" name="menuChoice" value="Cancel a Reservation Booking"/><br/><br/>
		<input type="submit" name="menuChoice" value="Reserve a Seat"/><br/><br/>
		<input type="submit" name="menuChoice" value="Book a Seat"/><br/><br/>
	</form>
	
	<input type="button" value="Return to Flight Selection" onclick="location.href='selectFlight'"/>
</body>
</html>