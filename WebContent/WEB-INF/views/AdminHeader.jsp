 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <c:set var="context" value="${pageContext.request.contextPath}"/>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-nav-demo" aria-expanded="false">
        			<span class="sr-only">Toggle navigation</span>
        			<span class="icon-bar"></span>
       	 			<span class="icon-bar"></span>
        			<span class="icon-bar"></span>
     			 </button>
				<a href="${context}/Home.jsp" class="navbar-brand">Restaurant Delivery</a>
			</div>
			<div class="collapse navbar-collapse" id="bs-nav-demo">
				<!-- If the user is not an admin send back to the error page -->
				<c:if test="${sessionScope.isAdmin == null}">
					<c:redirect url="Error.jsp" />
				</c:if>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="${context}/admin/"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> Home</a></li>
					<li><a href="${context}/admin/AdminAccountInfo">Account Information</a></li>
					<li><a href="${context}/admin/AdminNavigate">Navigate Application</a></li>
					<li><a href="${context}/LogoutServlet">Log Out <i class="fa fa-user" aria-hidden="true"></i></a></li>
				</ul>
			</div>
		</div>
	</nav>