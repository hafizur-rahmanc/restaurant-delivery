<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<script src="https://use.fontawesome.com/71c97a3df8.js"></script>
</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="container">
		<form action="LoginServlet" method="post">
		  <div class="form-group">
		   	<label for="userName">User Name</label>
		    <input type="text" class="form-control" name="userName" id="userName" placeholder="User Name">
		  </div>
		  <div class="form-group">
		    <label for="Password">Password</label>
		    <input type="password" class="form-control" name="password" id="Password" placeholder="Password">
		  </div>
		  <button type="submit" class="btn btn-default">Submit</button>
		</form>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>