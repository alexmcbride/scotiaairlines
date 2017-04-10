<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Scotia Airlines - Select Flight</title>
</head>
<body>

	<h1>Scotia Airlines - Select Flight</h1>
	
	<p>Please select a flight:</p>
			
	<!-- Loop through each flight and output it to page -->
	<c:forEach var="flight" items="${airline.flights}">
		<!-- Each flight gets its own form, so we can include a hidden input -->
		<form action="selectFlight" method="POST">
			<input type="submit" name="selectedFlight" value="ID: ${flight.value.flightNumber} From: ${flight.value.departure} To: ${flight.value.arrival}" />
			
			<!-- Store ID of flight in hidden input -->
			<input type="hidden" name="flightId" value="${flight.value.flightNumber}"/>
		</form>
		<br/>
	</c:forEach>
	
	<hr />
	<input type="button" value="Back to Main Menu" onclick="location.href='mainMenu'"/>
</body>
</html>