<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Scotia Airlines - Flight Administration</title>
</head>
<body>
<h1>Scotia Airlines - Flight Administration</h1>
<p>Please select admin option:</p>
	<form action="mainMenu" method="POST">
		<input type="submit" name="menuChoice" value="Change Flight Status"/><br/><br/>
	</form>
	
	<input type="button" value="Add New Flight" onclick="location.href='addNewFlight'" /><br/><br/>
	<input type="button" value="Return to Main Menu" onclick="location.href='mainMenu'" /><br/>
</body>
</html>