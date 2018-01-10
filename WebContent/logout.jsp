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
	<jsp:include page="header.jsp" />
	<div class="container">
		<div class="logout-content">
			<h3>Thanks for Visiting US!! You have been logged out</h3>
			<h3>Want to Log back in? Click below</h3>
			<div class="logout-page-button">
				<a href="login.jsp"><button class="btn btn-lg btn-primary">Login</button></a>
			</div>
		</div>
	</div>

</body>
</html>