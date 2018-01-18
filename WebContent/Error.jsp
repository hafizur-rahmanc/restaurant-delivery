<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error Page</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
	<script src="https://use.fontawesome.com/71c97a3df8.js"></script>
</head>
<body>
	<jsp:include page="Header.jsp" />
	<div class="container">
		<div class="error-content text-center" id="error-page">
			<h3>An Error has occurred while attempting to perform the previous request!</h3>
			<h4><a href="MenuItem.jsp">Please click here to go back to the Menu Items page</a></h4>
			<h4><a href="MenuItem.jsp">Menu</a></h4>
		</div>
	</div>
	<!-- Footer -->
	<jsp:include page="Footer.html" />
</body>
</html>