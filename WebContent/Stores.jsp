<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Location Page</title>
	<jsp:include page="HeaderLinks.jsp" />
</head>
<body>
<jsp:include page="Header.jsp" />
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
							<button class="btn btn-sm btn-primary" name="store" type="submit" value="${store.storeId}">Select Location</button>
						</div>
					</form>
				</div>
			</c:forEach>
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