<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Scotia Airlines - Get Company Name</title>
</head>
<body>
	<h1>Scotia Airlines - Get Company Name</h1>
	
	<form action="addInfo" method="POST">
		<label for="companyName">Company Name:</label>
		<input type="text" name="companyName"/>
		<br/><br/>
		<input type="submit" value="Submit"/>
	</form>
	
	<br/>
	
	<input type="button" value="Go back" onclick="location.href='bookingMenu'"/>
</body>
</html>