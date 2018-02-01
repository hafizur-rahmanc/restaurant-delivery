<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Admin Error Page</title>
<jsp:include page="AdminLinks.jsp" />
</head>
<body>
	<jsp:include page="AdminHeader.jsp" />
	<div class="container">
		<div class="error-content text-center" id="error-page">
			<h3>An Error has occurred while attempting to perform the previous request!</h3>
			<h4>Please <a href="MenuItemServlet">click here</a> to go back to the Menu Items page</h4>
			<h4><a href="MenuItemServlet">Menu</a></h4>
		</div>
	</div>
	<!-- Footer -->
	<jsp:include page="AdminFooter.html" />
	<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>