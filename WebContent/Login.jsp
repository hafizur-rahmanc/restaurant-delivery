<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Restaurant Delivery</title>
<jsp:include page="HeaderLinks.jsp" />
</head>
<body>
	<jsp:include page="Header.jsp" />
	<div class="container">
		<div class="jumbotron">
			<h2>Login</h2>
		</div>
		<!-- Show the appropriate alert message -->
		<c:if test="${message != null}">
			<div class="alert alert-info alert-dismissible" role="alert">
	  			<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	  			<p class="text-center"><strong id="message">${message}</strong></p>
			</div>
		</c:if>
		<form action="LoginServlet" method="post" class="form-horizontal">
		  <div class="form-group">
		   	<label for="userName" class="col-sm-3 control-label">User Name</label>
		   	<div class="col-sm-9">
		    	<input type="text" class="form-control" name="userName" id="userName" placeholder="User Name" required>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="Password" class="col-sm-3 control-label">Password</label>
		    <div class="col-sm-9">
		    	<input type="password" class="form-control" name="password" id="Password" placeholder="Password" required>
		    </div>
		  </div>
		  <div class="form-group">
			 <div class="col-sm-offset-3 col-sm-9">
			 	<button type="submit" class="btn btn-primary btn-block"  name="login" id="login">Login</button>
			 	<button type="submit" class="btn btn-success btn-block"  name="register" id="register" formnovalidate>Register</button>
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