<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Payment Information</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
	<script src="https://use.fontawesome.com/71c97a3df8.js"></script>
</head>
<body>
	<jsp:include page="Header.jsp" />
	<!-- Only display payment information form if the user is logged in-->
	<c:if test="${sessionScope.userId == null}">
		<c:redirect url="MenuItemServlet" />
	</c:if>
	<div class="container">
		<div class="jumbotron">
			<h2>Payment Information</h2>
		</div>
		<!-- Show the appropriate alert message -->
		<c:if test="${message != null}">
			<div class="alert alert-info alert-dismissible" role="alert">
	  			<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	  			<p class="text-center"><strong id="message">${message}</strong></p>
			</div>
		</c:if>
		<form action="PaymentInfoServlet" method="post" class="form-horizontal">
		  <div class="form-group">
		   	<label for="credit-card" class="col-sm-3 control-label">Credit Card</label>
		   	<div class="col-sm-9 nopadding">
		    	<input type="number" class="form-control" name="creditCard" id="creditCard" pattern=".{1,20}" placeholder="Credit Card Number" required title="Please enter a valid credit card number">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="secure-code" class="col-sm-3 control-label">Secure Number</label>
		    <div class="col-sm-9 no padding">
		    	<input type="number" class="form-control" name="secureCode" id="secure-code" pattern=".{3,}" placeholder="Secure Number" required title="Please enter a valid secure code (3 digits minimum)">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="zipcode" class="col-sm-3 control-label">Zip Code</label>
		    <div class="col-sm-9 no padding">
		    	<input type="number" class="form-control" name="zipcode" id="zipcode" pattern=".{5,}" placeholder="Zipcode" required title="Please enter a valid zip code (5 digits minimum)">
		    </div>
		  </div>
		  <div class="form-group">
			 <div class="col-sm-offset-3 col-sm-9 no padding">
			 	<button type="submit" class="btn btn-primary btn-block" name="process">Process</button>
			 </div>
		  </div>
		</form>
	</div>
	<!-- Footer -->
	<jsp:include page="Footer.html" />
	<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>