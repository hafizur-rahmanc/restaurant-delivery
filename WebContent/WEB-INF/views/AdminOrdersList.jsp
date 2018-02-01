<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Order's List</title>
<jsp:include page="AdminLinks.jsp" />
</head>
<body>
	<jsp:include page="AdminHeader.jsp" />
	<jsp:include page="NavigationPanel.jsp" />
	<!-- Redirect to the error page if user is not logged in -->
	<c:if test="${sessionScope.userId == null}">
		<c:redirect url="AdminError.jsp" />
	</c:if>
	<div class="container">
		<div class="text-center">
			<h3>List of Orders</h3>
		</div>
		<!-- Display message when order deleted successfully -->
		<c:if test="${message != null}">
			<div class="container">
				<div class="alert alert-info alert-dismissible" role="alert">
		  			<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		  			<p class="text-center"><strong id="message">${message}</strong></p>
				</div>
			</div>
		</c:if>
		<!-- Loop through each order and display it as a single order summary -->
		<c:forEach items="${ordersList}" var="order">
			<!-- Receipt Summary section -->
			<div class="col-sm-6">
				<div id="order-summary">
					<div class="price-details">
						<p><strong>Number of Items: ${order.orderItems.size()}</strong></p>
						<p><strong>Order Number: ${order.orderId}</strong>
						<p><strong>Total: $${order.totalPrice}</strong></p>
						<p><strong>Tax: $${order.taxAmount}</strong></p>
					</div>
				</div>
				<!-- Delete button of this order -->
				<div class="delete-button">
					<form action="${context}/admin/AdminDeleteOrder?orderId=${order.orderId}" method="post">
						<button class="btn btn-sm btn-block btn-danger" name="delete" id="delete-order">Delete</button>
					</form>
				</div>
			</div>
		</c:forEach>
	</div>
	<!-- Footer -->
	<jsp:include page="AdminFooter.html" />
	<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>