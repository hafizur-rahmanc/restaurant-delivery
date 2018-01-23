<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Locations List</title>
<jsp:include page="AdminLinks.jsp" />
</head>
<body>
	<jsp:include page="AdminHeader.jsp" />
	<jsp:include page="NavigationPanel.jsp" />
	<!-- When the location updated successfully -->
	<c:if test="${message != null}">
		<div class="container">
			<div class="alert alert-info alert-dismissible" role="alert">
	  			<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	  			<p class="text-center"><strong>${message}</strong></p>
			</div>
		</div>
	</c:if>
	
	<div class="container">
		<div class="row">
			<!-- Display all the locations to the left with the update option -->
			<div class="col-sm-6">
				<h3 class="text-center">Locations List</h3>
				<!-- Loop through the items list and display them one by one -->
				<c:forEach items="${storesList}" var="store">
					<div class="row">
						<form action="${context}/admin/AdminUpdateLocation" method="post" class="form-horizontal" id="items-list">
							<div class="form-group">
			   					<label for="storeName" class="col-sm-3 control-label">Location Name</label>
			   					<div class="col-sm-9">
			    					<input type="text" class="form-control" name="storeName" id="storeName" placeholder="Location Name" value="${store.storeName}" maxlength="20" required="required" title="Please enter upto 20 characters">
			    				</div>
			  				</div>
						  	<div class="form-group">
						   		<label for="storeAddress" class="col-sm-3 control-label">Address</label>
						   		<div class="col-sm-9">
						    		<input type="text" class="form-control" name="address" id="storeAddress" placeholder="Address" value="${store.address}" maxlength="20" required="required" title="Please enter upto 20 numbers">
						    	</div>
						  	</div>
							<div class="form-group">
							   	<label for="storeCity" class="col-sm-3 control-label">City</label>
							    <div class="col-sm-9">
							    	<input type="text" class="form-control" name="city" id="storeCity" placeholder="City" value="${store.city}" maxlength="20" required="required" title="Please enter upto 20 characters">
							    </div>
							 </div>
							 <div class="form-group">
							    <label for="staffNumber" class="col-sm-3 control-label">Staff Number</label>
							    <div class="col-sm-9">
							    	<input type="text" class="form-control" name="staffNumber" id="staffNumber" placeholder="Staff Number" value="${store.staffNumber}" maxlength="20" required="required" title="Please enter upto 20 characters">
							    </div>
							 </div>
							 <div class="form-group">
							    <label for="Zipcode" class="col-sm-3 control-label">ZipCode</label>
							    <div class="col-sm-9">
							    	<input type="text" class="form-control" name="zipcode" id="Zipcode" placeholder="Zip Code" value="${store.zipcode}" maxlength="20" required="required" title="Please enter upto 20 numbers">
							    </div>
							 </div>
							 <div class="form-group">
							    <label for="Image" class="col-sm-3 control-label">Image</label>
							    <div class="col-sm-9">
							    	<input type="text" class="form-control" name="image" id="Image" placeholder="Image" value="${store.image}" maxlength="30" required="required" title="Please enter upto 30 charcaters">
							    </div>
							 </div>

							 <div class="form-group">
							 	<div class="col-sm-offset-3 col-sm-9">
									<div class="admin-stores-list">
								 		<button type="submit" class="btn btn-primary btn-block" name="update" value="${store.storeId}">Update</button>
								 		<button type="submit" class="btn btn-danger btn-block" name="delete" value="${store.storeId}">Delete</button>
							 		</div>
							 	</div>
							 </div>
						</form>
					</div>
				</c:forEach>
			</div>
			<!-- Create a new item form -->
			<div class="col-sm-6">
			<div class="row">
				<h3 class="text-center">Add New Location</h3>
				<form:form action="${context}/admin/AdminAddLocation" method="post" class="form-horizontal" id="items-list" modelAttribute="store">
					<div class="form-group">
	   					<label for="storeName" class="col-sm-3 control-label">Location Name</label>
	   					<div class="col-sm-9">
	    					<form:input path="storeName" type="text" class="form-control" name="name" id="storeName" placeholder="Location Name"  maxlength="20" required="required" title="Please enter upto 20 characters" />
	    				</div>
	  				</div>
				  	<div class="form-group">
				   		<label for="address" class="col-sm-3 control-label">Address</label>
				   		<div class="col-sm-9">
				    		<form:input path="address" type="text" class="form-control" name="address" id="address" placeholder="Address" maxlength="20" required="required" title="Please enter upto 20 numbers" />
				    	</div>
				  	</div>
					<div class="form-group">
					   	<label for="city" class="col-sm-3 control-label">City</label>
					    <div class="col-sm-9">
					    	<form:input path="city" class="form-control" name="city" id="city" placeholder="City" maxlength="20" required="required" title="Please enter upto 20 characters" />
					    </div>
					 </div>
					 <div class="form-group">
					    <label for="itemImage" class="col-sm-3 control-label">Staff Number</label>
					    <div class="col-sm-9">
					    	<form:input path="staffNumber" type="text" class="form-control" name="staffNumber" id="staffNumber" placeholder="Staff NUmber" maxlength="20" required="required" title="Please enter upto 20 numbers" />
					    </div>
					 </div>
					 <div class="form-group">
					    <label for="zipcode" class="col-sm-3 control-label">Zip Code</label>
					    <div class="col-sm-9">
					    	<form:input path="zipcode" type="text" class="form-control" name="zipcode" id="zipcode" placeholder="Zip Code" maxlength="20" required="required" title="Please enter upto 20 numbers" />
					    </div>
					 </div>
					 <div class="form-group">
					    <label for="image" class="col-sm-3 control-label">Image</label>
					    <div class="col-sm-9">
					    	<form:input path="image" type="text" class="form-control" name="image" id="image" placeholder="Image" maxlength="30" required="required" title="Please enter upto 30 numbers" />
					    </div>
					 </div>

					 <div class="form-group">
					 	<div class="col-sm-offset-3 col-sm-9">
					 		<div class="admin-stores-list">
					 			<button type="submit" class="btn btn-primary btn-block" name="create">Add</button>
					 		</div>
					 	</div>
					 </div>
				</form:form>
			</div>
			</div>
			
		</div>
	</div>
	<jsp:include page="AdminFooter.html" />
	<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>