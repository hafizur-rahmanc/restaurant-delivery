<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Review Order</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
	<script src="https://use.fontawesome.com/71c97a3df8.js"></script>
</head>
<body>
<jsp:include page="header.jsp" />
	<!-- Only display process order or cancel order button if the user is logged in and added item to the cart -->
	<c:if test="${sessionScope.cartIds != null && sessionScope.userId != null }">
		<div class="container">
			<form action="ReviewOrderServlet" method="post">
				<div class="group-btn">
					<button class="btn btn-lg btn-success" type="submit" name="process" >Process Order</button>
					 <span class="btn-separator"></span>
					<button class="btn btn-lg btn-danger" type="submit" name="cancel" >Cancel Order</button>
				</div>
			</form>
		</div>
	</c:if>
	<!-- Go through each item in the cart and display the order summary -->
	<c:forEach items="${sessionScope.cartItems}" var="item">
		<div class="container" id="cartItems">
			<div class="row">
				<div class="col-sm-6 text-center">
					<div class="img-thumbnail">
						<img src="https://images.unsplash.com/photo-1432139509613-5c4255815697?auto=format&fit=crop&w=623&q=80" width="250" height="250">
					</div>
				</div>
				<div class="col-sm-6 text-left">
					<p><strong>${item.itemName}</strong></p>
					<p><strong>Price: $${item.itemPrice}</strong></p>
				</div>
				<div class="delete-item">
					<form action="ReviewOrder" method="post">
						<!-- Only display if the user is logged in -->
						<c:if test="${sessionScope.userId != null}">
							<button class="btn btn-sm btn-danger" type="submit" name="remove" value="${item.itemId}">Remove</button>
						</c:if>
					</form>
				</div>
			</div>
		</div>
	</c:forEach>

</body>
</html>