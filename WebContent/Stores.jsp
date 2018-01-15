<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Store Page</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
	<script src="https://use.fontawesome.com/71c97a3df8.js"></script>
</head>
<body>
<jsp:include page="header.jsp" />
	<c:if test="${cartIds != null }">
		<div class="container">
			<form action="StoreServlet?action=cancel" method="post">
				<button class="btn btn-lg btn-danger" type="submit" name="cancel" id="cancel">Cancel Order</button>
			</form>
		</div>
	</c:if>
	
	<div class="container">
		<div class="jumbotron">
			<h2><i class="fa fa-location-arrow"></i> Locations</h2>
		</div>
	
	<!-- Get All the Available items from contextScope and display it as individual div's -->
		<div class="row">
			<c:forEach items="${storesList}" var="store">
				<div class="col-lg-4 col-sm-6 text-center">
					<div class="img-thumbnail">
						<div class="text-center">
							<img alt="store-${store.storeId}" src="${pageContext.request.contextPath}/images/${store.image}" width="300" height="300">
						</div>
					</div>
					<div class="store-action">
						<p><strong>${store.storeName}</strong></p>
						<p><strong>Address: ${store.address}</strong></p>
					</div>
					<form action="StoreServlet?action=reviewOrder" method="post">
						<div class="store-operate">
							<button class="btn btn-sm btn-primary" name="storeId" type="submit" value="${store.storeId}">Select Location</button>
						</div>
					</form>
				</div>
			</c:forEach>
		</div>
	</div>	
	<!-- Footer -->
	<jsp:include page="Footer.html" />
	<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>