<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<div class="container">
	<div class="jumbotron">
		<ul class="list-unstyled" id="navigation-panel">
			<li><a href="${context}/admin/AdminUsersList">User List</a></li>
			<li><a href="${context}/admin/AdminItemsList">Item List</a></li>
			<li><a href="${context}/admin/AdminOrdersList">Order List</a></li>
			<li><a href="${context}/admin/AdminLocationsList">Location List</a></li>
		</ul>
	</div>
</div>
