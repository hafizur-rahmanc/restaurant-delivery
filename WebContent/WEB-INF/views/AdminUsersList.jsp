<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Users List</title>
<jsp:include page="AdminLinks.jsp" />
</head>
<body>
	<jsp:include page="AdminHeader.jsp" />
	<jsp:include page="NavigationPanel.jsp" />
	<h3 class="text-center" id="regular-user">Regular User's List</h3>
	<div class="container">
		<div class="table-responsive">
			<table class="table table-bordered">
				  <thead>
				    <tr>
				      <th scope="col">First Name</th>
				      <th scope="col">Last Name</th>
				      <th scope="col">Phone Number</th>
				      <th scope="col">Email Address</th>
				      <th scope="col">Action</th>
				    </tr>
				  </thead>
				  <tbody>
					  <!-- Loop through the users list and display them one by one -->
					  <c:forEach items="${usersList}" var="user">
					  	 <tr>
						     <td>${user.firstName}</td>
						     <td>${user.lastName}</td>
						     <td>${user.phoneNumber}</td>
						     <td>${user.email}</td>
						     <td>
						     	<div class="user-action">
						     		<a href="${context}/admin/AdminGetUser?userId=${user.userId}">
							     		<button class="btn btn-sm btn-primary" id="user-update">Update</button>
							     	</a>
							     <button class="btn btn-sm btn-danger">Delete</button>
							     </div>
						     </td>
						 </tr>
					  </c:forEach>
				  </tbody>
			</table>
		</div>
	</div>
	<jsp:include page="AdminFooter.html" />
</body>
</html>