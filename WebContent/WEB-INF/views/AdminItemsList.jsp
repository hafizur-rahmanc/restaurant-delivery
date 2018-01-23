<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" />
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Items List</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${context}/css/style.css">
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
	<script src="https://use.fontawesome.com/71c97a3df8.js"></script>
</head>
<body>
	<jsp:include page="AdminHeader.jsp" />
	<jsp:include page="NavigationPanel.jsp" />
	<!-- When the item updated successfully -->
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
			<!-- Display all the items to the left as an update form -->
			<div class="col-sm-6">
				<h3 class="text-center">Items List</h3>
				<!-- Loop through the items list and display them one by one -->
				<c:forEach items="${itemsList}" var="item">
					<div class="row">
						<form action="${context}/admin/UpdateItem" method="post" class="form-horizontal" id="items-list">
							<div class="form-group">
			   					<label for="itemName" class="col-sm-3 control-label">Item Name</label>
			   					<div class="col-sm-9">
			    					<input type="text" class="form-control" name="name" id="itemName" placeholder="Item Name" value="${item.itemName}" maxlength="20" required="required" title="Please enter upto 20 characters">
			    				</div>
			  				</div>
						  	<div class="form-group">
						   		<label for="itemPrice" class="col-sm-3 control-label">Price</label>
						   		<div class="col-sm-9">
						    		<input type="text" class="form-control" name="price" id="itemPrice" placeholder="Item Price"value="${item.itemPrice}" maxlength="20" required="required" title="Please enter upto 20 numbers">
						    	</div>
						  	</div>
							<div class="form-group">
							   	<label for="itemDescription" class="col-sm-3 control-label">Item Description</label>
							    <div class="col-sm-9">
							    	<textarea rows="3" class="form-control" name="description" id="itemDescription" placeholder="Item Description" maxlength="500" required="required" title="Please enter upto 500 characters">${item.itemDescription}</textarea>
							    </div>
							 </div>
							 <div class="form-group">
							    <label for="itemImage" class="col-sm-3 control-label">Item Image</label>
							    <div class="col-sm-9">
							    	<input type="text" class="form-control" name="image" id="itemImage" placeholder="Item Image" value="${item.image}" maxlength="20" required="required" title="Please enter upto 20 characters">
							    </div>
							 </div>
							 <div class="form-group">
							 	<label class="col-sm-3 control-label">Active</label>
				    			<div class="col-sm-9">
							 		<label class="radio-inline">
				    					<input type="radio" name="active" id="active" value="1" ${item.active == 1 ? 'checked' : ''} required="required">Active
									</label>
				  					<label class="radio-inline">
										<input type="radio" name="active" id="not active" value="0" ${item.active == '0' ? 'checked' : ''} required="required">Not Active
				  					</label>
								</div>
							</div>
							 <div class="form-group">
							    <label for="Category" class="col-sm-3 control-label">Category</label>
							    <div class="col-sm-9">
							    	<input type="text" class="form-control" name="category" id="Category" placeholder="Category" value="${item.category}" maxlength="20" required="required" title="Please enter upto 20 charcaters">
							    </div>
							 </div>

							 <div class="form-group">
							 	<div class="col-sm-offset-3 col-sm-9">
									<div class="admin-items-list">
								 		<button type="submit" class="btn btn-primary btn-block" name="update" value="${item.itemId}">Update</button>
								 		<button type="submit" class="btn btn-danger btn-block" name="delete" value="${item.itemId}">Delete</button>
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
				<h3 class="text-center">Add New Item</h3>
				<form:form action="${context}/admin/AddItem" method="post" class="form-horizontal" id="items-list" modelAttribute="item">
					<div class="form-group">
	   					<label for="itemName" class="col-sm-3 control-label">Item Name</label>
	   					<div class="col-sm-9">
	    					<form:input path="itemName" type="text" class="form-control" name="name" id="itemName" placeholder="Item Name"  maxlength="20" required="required" title="Please enter upto 20 characters" />
	    				</div>
	  				</div>
				  	<div class="form-group">
				   		<label for="itemPrice" class="col-sm-3 control-label">Price</label>
				   		<div class="col-sm-9">
				    		<form:input path="itemPrice" type="text" class="form-control" name="price" id="itemPrice" placeholder="Item Price" maxlength="20" required="required" title="Please enter upto 20 numbers" />
				    	</div>
				  	</div>
					<div class="form-group">
					   	<label for="itemDescription" class="col-sm-3 control-label">Item Description</label>
					    <div class="col-sm-9">
					    	<form:textarea path="itemDescription" rows="3" class="form-control" name="description" id="itemDescription" placeholder="Item Description" maxlength="500" required="required" title="Please enter upto 500 characters" />
					    </div>
					 </div>
					 <div class="form-group">
					    <label for="itemImage" class="col-sm-3 control-label">Item Image</label>
					    <div class="col-sm-9">
					    	<form:input path="image" type="text" class="form-control" name="image" id="itemImage" placeholder="Item Image" maxlength="20" required="required" title="Please enter upto 20 characters" />
					    </div>
					 </div>
					 <div class="form-group">
					 	<label class="col-sm-3 control-label">Active</label>
		    			<div class="col-sm-9">
					 		<label class="radio-inline">
		    					<form:radiobutton path="active"  name="active" id="active" value="1"  required="required" />Active
							</label>
		  					<label class="radio-inline">
								<form:radiobutton path="active" name="active" id="not active" value="0" required="required" />Not Active
		  					</label>
						</div>
					</div>
					 <div class="form-group">
					    <label for="Category" class="col-sm-3 control-label">Category</label>
					    <div class="col-sm-9">
					    	<form:input path="category" type="text" class="form-control" name="category" id="Category" placeholder="Category" maxlength="20" required="required" title="Please enter upto 20 charcaters" />
					    </div>
					 </div>

					 <div class="form-group">
					 	<div class="col-sm-offset-3 col-sm-9">
					 		<div class="admin-items-list">
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