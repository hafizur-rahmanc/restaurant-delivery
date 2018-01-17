<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Logout Page</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
	<script src="https://use.fontawesome.com/71c97a3df8.js"></script>
</head>
<body>
	<jsp:include page="Header.jsp" />
	<div class="container">
		<div class="logout-content">
			<h4>Thanks for Visiting US!! You have been logged out</h4>
			<h4>Want to Log back in? Click below</h4>
			<div class="logout-page-button">
				<a href="Login.jsp"><button class="btn btn-md btn-primary">Login</button></a>
			</div>
		</div>
	</div>
	<!-- Footer -->
	<jsp:include page="Footer.html" />
</body>
</html>