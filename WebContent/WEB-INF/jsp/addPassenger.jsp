<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Scotia Airlines - Add Passenger</title>
</head>
<body>
	<h1>Scotia Airlines - Add Passenger</h1>
	
	<form action="addPassenger" method="POST">
		<label for="passengerName">Enter Passenger Name:</label>
		<input type="text" name="passengerName"/>

		<br/><br />
		<label for="passengerType">Select Passenger Type:</label>
		<select name="passengerType">
			<option disabled="disabled">---- Choose Option ----</option>
			<option value="O">Ordinary Passenger</option>
			<option value="I">Island Resident</option>
			<option value="B">Business Passenger</option>
		</select>
	
		<br/>
		<br/>
			
		<input type="submit" value="Submit"/>
	</form>
	
	<br/>
	<input type="button" value="Go back" onclick="location.href='bookingMenu'"/>
	
</body>
</html>