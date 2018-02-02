<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Item Review</title>
<jsp:include page="HeaderLinks.jsp" />
</head>
<body>
<jsp:include page="Header.jsp" />
	<div class="container">
		<div class="jumbotron">
			<h2>Item Review</h2>
		</div>
		<!-- Show the appropriate alert message -->
		<c:if test="${message != null}">
			<div class="alert alert-info alert-dismissible" role="alert">
	  			<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	  			<p class="text-center"><strong id="message">${message}</strong></p>
			</div>
		</c:if>
		<div class="row align-items-center">
			<div class="col sm-4 col-sm-6 item-image">
				<div class="text-center">
					<img src="${pageContext.request.contextPath}/images/${item.image}" class="img-rounded" width="150" height="150">
				</div>
			</div>
			
			<div class="col sm-4 col-sm-6" id="product-description">
				<div>
					<p><strong id="item-name">${item.itemName}</strong></p>
					<p><strong id="item-price">Price: $${item.itemPrice}</strong></p>
				</div>
				<div>
					<!-- Only display if the user is logged in -->
					<c:if test="${sessionScope.userId != null}">
						<button class="btn btn-sm btn-primary" data-toggle="modal" data-target="#myModal-${item.itemId}" name="item" value="${item.itemId}">Add To Cart</button>
					</c:if>
				</div>
				<div class="item-description">
					<p id="item-desc">${item.itemDescription}</p>
				</div>
			</div>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="myModal-${item.itemId}" role="dialog">
		    <div class="modal-dialog">
		    
		      <!-- Modal content-->
		      <div class="modal-content">
		        <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal">×</button>
		          <h4><span class="glyphicon glyphicon-lock"></span> Item</h4>
		        </div>
		        <div class="modal-body">
		          <form role="form" action="MenuItemServlet" method="post">
		            <div class="form-group">
		              <label for="psw"><span class="glyphicon glyphicon-shopping-cart"></span>$${item.itemPrice} Each</label>
		              <input type="number" class="form-control" id="psw" placeholder="How many?">
		            </div>
		              <button type="submit" class="btn btn-primary btn-block" name="add-item" value="${item.itemId}">Add 
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
	<!-- Review section -->
	<div class="container">
		<div class="row align-items-center">
			<div class="col-sm-offset-2 col-sm-6 text-center" id="review-form">
				<c:if test="${sessionScope.userId != null}">
					<h3>Write a Review</h3>
					<form action="ItemServlet?itemId=${item.itemId}" method="post">
					  <div class="form-group">
					  	<textarea rows="3" class="form-control" name="itemReview" required maxlength="500" ></textarea>
					  	</div>
						 <div class="review-submit">
						 	<button type="submit" class="btn btn-success btn-block" name="review" value="${param.itemId}">Post Review</button>
						 </div>
					</form>
				</c:if>
			</div>
			<!-- Past Reviews -->
			<div class="col-sm-4 text-center" id="past-review">
				<h3 class="text-center">Reviews</h3>
				<!-- Loop through the itemReviews -->
				<c:forEach items="${item.itemReviews}" var="review">
					<div class="item-review">
						<h4><strong>${review.userName}:</strong></h4>
						<p class="review-description">${review.description}</p>
					</div>
				</c:forEach>
			</div> 
		</div>
	</div>
	
	<div class="jumbotron" id="hidden">
	</div>
	<!-- Footer -->
	<jsp:include page="Footer.html" />
	<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>