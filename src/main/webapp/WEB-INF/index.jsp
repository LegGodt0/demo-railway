	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Ninja Gold Game</title>
		<link href="css/styles.css" rel="stylesheet" />
	</head>
	<body>
		<header>
			<p>Your Gold: <span class="amount">${gold}</span></p>
			<form action="/Gold" method="POST">
				<input type="hidden" name="cantidad" value="5">
				<input type="submit" value="Reset"/>
			</form>
		</header>

		<main>
			<section>
				<h3>Farm</h3>
				<p>(earns 10-20 gold)</p>
				<form action="/Gold" method="POST">
					<input type="hidden" name="cantidad" value="0">
					<input type="submit" value="Find Gold!"/>
				</form>
			</section>
			
			<section>
				<h3>Cave</h3>
				<p>(earns 5-10 gold)</p>
				<form action="/Gold" method="POST">
					<input type="hidden" name="cantidad" value="1">
					<input type="submit" value="Find Gold!"/>
				</form>
			</section>
			
			<section>
				<h3>House</h3>
				<p>(earns 2-5 gold)</p>
				<form action="/Gold" method="POST">
					<input type="hidden" name="cantidad" value="2">
					<input type="submit" value="Find Gold!"/>
				</form>
			</section>
			
			<section>
				<h3>Casino!</h3>
				<p>(earns/takes 0 - 50 gold)</p>
				<form action="/Gold" method="POST">
					<input type="hidden" name="cantidad" value="3">
					<input type="submit" value="Find Gold!"/>
				</form>
			</section>
			
			<section>
				<h3>Spa</h3>
				<p>(takes 5 - 20 gold)</p>
				<form action="/Gold" method="POST">
					<input type="hidden" name="cantidad" value="4">
					<input type="submit" value="Find Gold!"/>
				</form>
			</section>
			
			<div class="activities">
				<h3>Activities:</h3>
				<div class="caja">
					<ul class="contenido">
						<c:forEach var="nombre" items="${message}">
							<li>
								${nombre}
							</li>
				        </c:forEach>
					</ul>
				</div>
			</div>
		</main>
		
		
		
		
		
		
		
	</body>
</html>