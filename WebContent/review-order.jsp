<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Review Order Page</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
	<script src="https://use.fontawesome.com/71c97a3df8.js"></script>
</head>
<body>
<jsp:include page="header.jsp" />
	<c:if test="${cartIds != null }">
		<div class="container">
			<form action="ReviewOrderServlet" method="post">
				<div class="orderOperation">
					<button class="btn btn-lg btn-success" type="submit" name="process" >Process Order</button>
					<button class="btn btn-lg btn-danger" type="submit" name="cancel" >Cancel Order</button>
				</div>
			</form>
		</div>
	</c:if>

</body>
</html>