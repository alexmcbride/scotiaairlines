<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Scotia Airlines - Main Menu</title>
</head>
<body>
	<h1>Scotia Airlines - Main Menu</h1>
	<p>Please select an option:</p>
	
	<input type="button" class="btn btn-primary" value="Flight Administration" onclick="location.href='flightAdmin'" /><br/><br/>

	<form action="mainMenu" method="POST">
		<input type="submit" name="menuChoice" value="Booking Menu" /><br/><br/>	
		<input type="submit" name="menuChoice" value="Display Flight Seats" /><br/><br/>
		<input type="submit" name="menuChoice" value="Display Flight Details" />
	</form>
</body>
</html>