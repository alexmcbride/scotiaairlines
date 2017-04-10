<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Scotia Airlines - Add Flight</title>
</head>
<body>
<h1>Scotia Airlines - Add New Flight</h1>

<c:if test="${not empty message}">
	<p><strong>${message}</strong></p>
</c:if>

<form method="post" action="addNewFlight">
	<table>
		<tr>
			<td><label for="flightId">Flight ID:</label></td>
			<td height="20px"><input type="text" name="flightId"/></td>
		</tr>
		<tr>
			<td><label for="departure">Departure:</label></td>
			<td><input type="text" name="departure"/></td>
		</tr>
		<tr>
			<td><label for="arrival">Arrival:</label></td>
			<td><input type="text" name="arrival"/></td>
		</tr>
		<tr>
			<td><label for="rows">No. of Rows:</label></td>
			<td><input type="text" name="rows"/></td>
		</tr>
		<tr>
			<td><label for="columns">No. of Columns:</label></td>
			<td><input type="text" name="columns"/></td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="Submit"/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="button" value="Clear" onclick="location.href='addNewFlight'"/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="button" value="Return to Admin Options" onclick="location.href='flightAdmin'"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>