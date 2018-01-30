<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Details</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
	<script src="https://use.fontawesome.com/71c97a3df8.js"></script>
</head>
<body>
	<jsp:include page="Header.jsp" />
	<div class="container">
		<div class="jumbotron">
			<h2>Registration</h2>
		</div>
		<!-- Show the appropriate alert messgae -->
		<c:if test="${message != null}">
			<div class="alert alert-info alert-dismissible" role="alert">
	  			<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	  			<p class="text-center"><strong id="message">${message}</strong></p>
			</div>
		</c:if>
		<form action="RegisterServlet" method="post" class="form-horizontal">
			<div class="form-group">
		   		<label for="firstName" class="col-sm-3 control-label">First Name</label>
		   		<div class="col-sm-9">
		    		<input type="text" class="form-control" name="firstName" id="firstName" placeholder="First Name" required>
		    	</div>
		  	</div>
		  	<div class="form-group">
		   		<label for="lastName" class="col-sm-3 control-label">Last Name</label>
		   		<div class="col-sm-9">
		    		<input type="text" class="form-control" name="lastName" id="lastName" placeholder="Last Name" required>
		    	</div>
		  	</div>
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
			    <label for="RePassword" class="col-sm-3 control-label">Re-Password</label>
			    <div class="col-sm-9">
			    	<input type="password" class="form-control" name="repassword" id="RePassword" placeholder="Re-enter Password" required>
			    </div>
			 </div>
			 <div class="form-group">
			 	<label class="col-sm-3 control-label">Gender</label>
    			<div class="col-sm-9">
			 		<label class="radio-inline">
    					<input type="radio" name="gender" id="Male" value="M" required>Male
					</label>
  					<label class="radio-inline">
						<input type="radio" name="gender" id="Female" value="F" required>Female
  					</label>
				</div>
			</div>
			 <div class="form-group">
			    <label for="Address" class="col-sm-3 control-label">Address</label>
			    <div class="col-sm-9">
			    	<input type="text" class="form-control" name="address" id="Address" placeholder="Address" required>
			    </div>
			 </div>
			 <div class="form-group">
			    <label for="PhoneNumber" class="col-sm-3 control-label">Phone Number</label>
			    <div class="col-sm-9">
			    	<input type="number" class="form-control" name="phoneNumber" id="PhoneNumber" maxlength="10" placeholder="Phone Number" required>
			    </div>
			 </div>
			 <div class="form-group">
			    <label for="Email" class="col-sm-3 control-label">Email</label>
			    <div class="col-sm-9">
			    	<input type="email" class="form-control" name="email" id="Email" placeholder="Email" required>
			    </div>
			 </div>
			 <div class="form-group">
			 	<div class="col-sm-offset-3 col-sm-9">
			 		<button type="submit" class="btn btn-primary btn-block" id="register" name="register">Register</button>
			 		<button type="submit" class="btn btn-success btn-block"  name="login" id="login" formnovalidate>Login</button>
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
