<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
	<script src="https://use.fontawesome.com/71c97a3df8.js"></script>
</head>
<body id="myPage" data-spy="scroll" data-target=".navbar" data-offset="50">
<jsp:include page="Header.jsp" />
	<div class="container">
		<div class="jumbotron">
			<h2><i class="fa fa-cutlery" aria-hidden="true"></i> Welcome to the Restaurant Delivery</h2>
		</div>
		<div class="text-center">
			<div class="img-thumbnail">
				<img src="${pageContext.request.contextPath}/images/restaurant-delivery.jpg" width="768" height="450">
			</div>
		</div>
		<div class="row quote-text">
			<!-- Text to the left -->
			<div class="col-sm-6 text-center" id="text-left">
				<p>"One cannot think well, love well, sleep well</p>
				<p>if one has not DINED well"</p>
			</div>
			<!-- Text to the right -->
			<div class="col-sm-6">
				<div class="text-center">
					<p>Pizza might just be the ultimate delivery meal, so</p>
					<p>why not up the ante a bit? This Clinton Hill</p>
					<p>pizzeria by husband-and-wife team. Matt and</p>
					<p>Emilly Hyland has only been open since early</p>
					<p>2014, but its dough, mozzarella - both made in-</p>
					<p>house, by hand - and wood-brning oven have</p>
					<p>quickly established it as a force</p>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="Footer.html" />
	<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>