<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<div class="container">
	<div class="jumbotron">
		<ul class="list-unstyled" id="navigation-panel">
			<li><a href="${context}/admin/AdminUsersList" id="user-list">User List</a></li>
			<li><a href="${context}/admin/AdminItemsList" id="item-list">Item List</a></li>
			<li><a href="${context}/admin/AdminOrdersList" id="order-list">Order List</a></li>
			<li><a href="${context}/admin/AdminLocationsList" id="location-list">Location List</a></li>
		</ul>
	</div>
</div>
