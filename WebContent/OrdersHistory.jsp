<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Order's History</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
	<script type="text/javascript" src="js/index.js"></script>
	<script src="https://use.fontawesome.com/71c97a3df8.js"></script>
</head>
<body>
<jsp:include page="Header.jsp" />
	<!-- Redirect to the error page if user is not logged in -->
	<c:if test="${sessionScope.userId == null}">
		<c:redirect url="Error.jsp" />
	</c:if>
	<div class="container">
		<div class="jumbotron">
			<h2>Order's History</h2>
		</div>
		<!-- Assign the ordersList to a variable -->
		<c:set var="ordersList" value="${requestScope.ordersList}" />
		<!-- Loop through each order and display it as a single order summary -->
		<c:forEach items="${ordersList}" var="order">
			<div class="row align-items-center">
				<!-- Item description section -->
				<div class="col-sm-6">
					<c:forEach items="${order.orderItems}" var="item">
						<div class="row allign-items-center">
							<div class="col-sm-8 item-image">
								<img alt="item-${item.itemId}" src="${pageContext.request.contextPath}/images/${item.image}" class="img-rounded" width="140" height="140">
							</div>
							<div class="col-sm-4 product-description">
								<p><strong>$${item.itemPrice}</strong>
								<p><strong>${item.itemName}</strong></p>
							</div>
						</div>
					</c:forEach>
				</div>
				<!-- Receipt Summary section -->
				<div class="col-sm-6 receipt-summary">
					<div id="price-summary">
						<div class="price-details">
							<p><strong>Total: $${order.totalPrice}</strong></p>
							<p><strong>Tax: $${order.taxAmount}</strong></p>
							<p><strong>Items: ${order.orderItems.size()}</strong></p>
							<p><strong>Order Number: ${order.orderId}</strong>
						</div>
						 &nbsp;
						 <!-- Store information of this order -->
						<div class="store-info">
							<p><strong>${order.store.storeName}</strong></p>
							<p><strong>${order.store.address}</strong></p>
							<p><strong>${order.store.city}, ${order.store.zipcode}</strong></p>
						</div>
					</div>
				</div>
			</div>
			<hr>
		</c:forEach>
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