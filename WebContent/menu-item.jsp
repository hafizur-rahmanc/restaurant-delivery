<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<script src="https://use.fontawesome.com/71c97a3df8.js"></script>
</head>
<body>
<jsp:include page="header.jsp" />
	<div class="container">
		<div class="jumbotron">
			<h1><i class="fa fa-cutlery" aria-hidden="true"></i> Menu Items</h1>
			<p>All the available menu items</p>
		</div>

		<div class="row">
			<div class="col-lg-4 col-sm-6">
				<div class="img-thumbnail">
					<img src="https://images.unsplash.com/photo-1432139509613-5c4255815697?auto=format&fit=crop&w=140&q=140">
				</div>
			</div>
			<div class="col-lg-4 col-sm-6">
				<div class="img-thumbnail">
					<img src="https://images.unsplash.com/photo-1504185945330-7a3ca1380535?auto=format&fit=crop&w=140&q=140">
				</div>
			</div>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>