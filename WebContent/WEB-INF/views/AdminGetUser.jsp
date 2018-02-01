<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Account Info</title>
<jsp:include page="AdminLinks.jsp" />
</head>
<body>
	<jsp:include page="AdminHeader.jsp" />
	<jsp:include page="NavigationPanel.jsp" />
	<!-- Redirect to the error page if user is not logged in -->
	<c:if test="${sessionScope.userId == null}">
		<c:redirect url="AdminError.jsp" />
	</c:if>
	
	<!-- Display the appropriate alert message -->
	<c:if test="${message != null}">
		<div class="alert alert-info alert-dismissible" role="alert">
  			<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  			<p class="text-center"><strong id="message">${message}</strong></p>
		</div>
	</c:if>
	<div class="container">
		<div class="row">
		<div class="col-sm-6">
			<h3 class="text-center">Regular User Account Information</h3>
		<form:form action="${context}/admin/AdminUpdateUser" method="post" class="form-horizontal">
			<form:input path="userId" hidden="true" />
			<div class="form-group">
		   		<label for="firstName" class="col-sm-3 control-label">First Name</label>
		   		<div class="col-sm-9">
		    		<form:input path="firstName" type="text" class="form-control" name="firstName" id="firstName" placeholder="First Name" maxlength="20" required="required" title="Please enter upto 20 characters" />
		    	</div>
		  	</div>
		  	<div class="form-group">
		   		<label for="lastName" class="col-sm-3 control-label">Last Name</label>
		   		<div class="col-sm-9">
		    		<form:input path="lastName" type="text" class="form-control" name="lastName" id="lastName" placeholder="Last Name" maxlength="20" required="required" title="Please enter upto 20 characters" />
		    	</div>
		  	</div>
			<div class="form-group">
			   	<label for="userName" class="col-sm-3 control-label">User Name</label>
			    <div class="col-sm-9">
			    	<form:input path="userName" type="text" class="form-control" name="userName" id="userName" placeholder="User Name" maxlength="20" required="required" title="Please enter upto 20 characters" />
			    </div>
			 </div>
			 <div class="form-group">
			    <label for="Password" class="col-sm-3 control-label">Password</label>
			    <div class="col-sm-9">
			    	<form:input path="password" type="password" class="form-control" name="password" id="Password" placeholder="Password" maxlength="20" required="required" title="Please enter upto 20 characters"/>
			    </div>
			 </div>
			 <div class="form-group">
			    <label for="RePassword" class="col-sm-3 control-label">Re-Password</label>
			    <div class="col-sm-9">
			    	<form:input path="repassword" type="password" class="form-control" name="rePassword" id="RePassword" placeholder="Re-enter Password" maxlength="20" required="required" title="Please enter upto 20 characters" />
			    </div>
			 </div>
			 <div class="form-group">
			 	<label class="col-sm-3 control-label">Gender</label>
    			<div class="col-sm-9">
			 		<label class="radio-inline">
    					<form:radiobutton path="gender" name="gender" id="Male" value="M" required="required" />Male
					</label>
  					<label class="radio-inline">
						<form:radiobutton path="gender" name="gender" id="Female" value="F" required="required" />Female
  					</label>
				</div>
			</div>
			 <div class="form-group">
			    <label for="Address" class="col-sm-3 control-label">Address</label>
			    <div class="col-sm-9">
			    	<form:input path="address" type="text" class="form-control" name="address" id="Address" pattern="[a-ZA-ZO-9 .]{1,100}" placeholder="Address" maxlength="100" required="required" title="Please enter upto 100 charcaters" />
			    </div>
			 </div>
			 <div class="form-group">
			    <label for="PhoneNumber" class="col-sm-3 control-label">Phone Number</label>
			    <div class="col-sm-9">
			    	<form:input path="phoneNumber" type="number" class="form-control" name="phoneNumber" id="PhoneNumber" pattern="[0-9]{10}" maxlength="10" placeholder="Phone Number" required="required" title="Please enter  a 10 digit numeric phone number" />
			    </div>
			 </div>
			 <div class="form-group">
			    <label for="Email" class="col-sm-3 control-label">Email</label>
			    <div class="col-sm-9">
			    	<form:input path="email" type="email" class="form-control" name="email" id="Email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" placeholder="Email" required="required" title="Please enter a valid email" />
			    </div>
			 </div>
			 <div class="form-group">
			 	<div class="col-sm-offset-3 col-sm-9">
			 		<button type="submit" class="btn btn-primary btn-block" id="update">Update</button>
			 	</div>
			 </div>
		</form:form>
		</div>
		<!-- User reviews section -->
		<div class="col-sm-6">
			<h3 class="text-center">User Reviews</h3>
			<!-- Past Reviews -->
			<div class="text-center" id="past-review">
				<!-- Loop through the itemReviews -->
				<c:forEach items="${reviewsList}" var="review">
					<div class="item-review">
						<h4><strong>${review.userName}:</strong></h4>
						<p id="review-description">${review.description}</p>
						<form action="${context}/admin/AdminDeleteReview?reviewId=${review.reviewId}" method="post">
							<button class="btn btn-sm btn-danger" name="delete" value="${review.userId}">Delete</button>
						</form>
						<hr />
					</div>
				</c:forEach>
			</div>
		</div>
		</div>
	</div>
	<!-- Footer -->
	<jsp:include page="AdminFooter.html" />
	<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>