<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Menu Page</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
	<script src="https://use.fontawesome.com/71c97a3df8.js"></script>
</head>
<body>
<jsp:include page="header.jsp" />
	<div class="container">
		<div class="jumbotron">
			<h1><i class="fa fa-cutlery" aria-hidden="true"></i> Menu Items</h1>
			<p>All the available menu items</p>
		</div>
		
		<!-- Get All the Available items from sessionScope and display it as individual div -->
		<div class="row text-center">
			<c:forEach items="${itemsList}" var="item">
				<div class="col-lg-4 col-sm-6">
					<div class="img-thumbnail">
						<img src="https://images.unsplash.com/photo-1432139509613-5c4255815697?auto=format&fit=crop&w=623&q=80" width="300" height="300">
					</div>
					<div class="item-action">
						<p><strong>${item.itemName}</strong></p>
						<p>
							<span class="input-addon"><strong>Price:</strong> $</span><strong> ${item.itemPrice}</strong>
						</p>
					</div>
					<div class="item-operate">
						<button class="btn btn-sm btn-primary" data-toggle="modal" data-target="#myModal" name="itemId" value="${item.itemId}">Add To Cart</button>
					</div>
				</div>
			</c:forEach>
		</div>

		<!-- Modal -->
		  <div class="modal fade" id="myModal" role="dialog">
		    <div class="modal-dialog">
		    
		      <!-- Modal content-->
		      <div class="modal-content">
		        <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal">×</button>
		          <h4><span class="glyphicon glyphicon-lock"></span> Item</h4>
		        </div>
		        <div class="modal-body">
		          <form role="form" action="CartServlet" method="post">
		            <div class="form-group">
		              <label for="psw"><span class="glyphicon glyphicon-shopping-cart"></span> $10.99 Each</label>
		              <input type="number" class="form-control" id="psw" placeholder="How many?">
		            </div>
		              <button type="submit" class="btn btn-primary btn-block" name="itemId" value="${1}">Add 
		                <span class="glyphicon glyphicon-ok"></span>
		              </button>
		          </form>
		        </div>
		        <div class="modal-footer">
		          <button type="submit" class="btn btn-danger btn-default pull-left" data-dismiss="modal">
		            <span class="glyphicon glyphicon-remove"></span> Cancel
		          </button>
		          <p>Need <a href="#">help?</a></p>
		        </div>
		      </div>
		    </div>
		  </div>
	</div>

	<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>