<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Insert title here</title>
	</head>
	<body>
		<h1> You went to prison because you have a debt of ${gold} !!</h1>
		<form action="/Gold" method="POST">
			<input type="hidden" name="cantidad" value="5">
			<input type="submit" value="Reset"/>
		</form>
	</body>
</html>