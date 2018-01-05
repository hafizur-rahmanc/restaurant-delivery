<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration Page</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<script src="https://use.fontawesome.com/71c97a3df8.js"></script>
</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="container">
		<form action="UserControllerServlet" method="post">
			<div class="form-group">
		   		<label for="firstName">First Name</label>
		    	<input type="text" class="form-control" name="firstName" id="firstName" placeholder="First Name" required>
		  	</div>
		  	<div class="form-group">
		   		<label for="lastName">Last Name</label>
		    	<input type="text" class="form-control" name="lastName" id="lastName" placeholder="Last Name" required>
		  	</div>
			<div class="form-group">
			   	<label for="userName">User Name</label>
			    <input type="text" class="form-control" name="userName" id="userName" placeholder="User Name" required>
			 </div>
			 <div class="form-group">
			    <label for="Password">Password</label>
			    <input type="password" class="form-control" name="password" id="Password" placeholder="Password" required>
			 </div>
			 
			 <div class="radio">
  				<label>
    				<input type="radio" name="gender" id="Male" value="M" required>Male
  				</label>
			</div>
			<div class="radio">
  				<label>
    				<input type="radio" name="gender" id="Female" value="F" required>Female
  				</label>
			</div>
			 <div class="form-group">
			    <label for="Address">Address</label>
			    <input type="text" class="form-control" name="address" id="Address" placeholder="Address" required>
			 </div>
			 <div class="form-group">
			    <label for="PhoneNumber">Phone Number</label>
			    <input type="number" class="form-control" name="phoneNumber" id="PhoneNumber" maxlength="10" placeholder="Phone Number" required>
			 </div>
			 <div class="form-group">
			    <label for="Email">Email</label>
			    <input type="email" class="form-control" name="email" id="Email" placeholder="Email" required>
			 </div>
			 
		  	<button type="submit" class="btn btn-default">Sign Up</button>
		</form>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
