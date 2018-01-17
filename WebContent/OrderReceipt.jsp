<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Order Receipt</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
	<script src="https://use.fontawesome.com/71c97a3df8.js"></script>
</head>
<body>
<jsp:include page="Header.jsp" />
	<c:if test="${sessionScope.userId == null}">
		<c:redirect url="MenuItemServlet" />
	</c:if>
	<div class="container">
		<div class="jumbotron">
			<h2>Order's Receipt</h2>
		</div>
		
		<c:set var="orderItems" value="${requestScope.orderItems}" />
		<div class="row align-items-center">
			<div class="col-sm-6">
				<c:forEach items="${orderItems}" var="item">
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
			<div class="col-sm-6 receipt-summary">
				<div id="price-summary">
					<div class="price-details">
						<p><strong>Total: $${receiptSummary.totalPrice}</strong></p>
						<p><strong>Tax: $${receiptSummary.taxAmount}</strong></p>
						<p><strong>Items: ${orderItems.size()}</strong></p>
					</div>
					 &nbsp;
					<div class="store-info">
						<p><strong>${receiptSummary.store.storeName}</strong></p>
						<p><strong>${receiptSummary.store.address}</strong></p>
						<p><strong>${receiptSummary.store.city}, ${receiptSummary.store.zipcode}</strong></p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="jumbotron" id="hidden">
	</div>
	<!-- Footer -->
	<jsp:include page="Footer.html" />
</body>
</html>