 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-nav-demo" aria-expanded="false">
        			<span class="sr-only">Toggle navigation</span>
        			<span class="icon-bar"></span>
       	 			<span class="icon-bar"></span>
        			<span class="icon-bar"></span>
     			 </button>
				<a href="Home.jsp" class="navbar-brand"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> Restaurant Delivery</a>
			</div>
			<div class="collapse navbar-collapse" id="bs-nav-demo">
				<c:set var="userId" value="${sessionScope.userId}" />
				<ul class="nav navbar-nav navbar-right">
				<c:choose>
					<c:when test="${userId != null}">
						<li><a href="Home.jsp"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> Home</a></li>
						<li><a href="MenuItemServlet">Menu</a></li>
						<li><a href="OrdersHistoryServlet">View Orders</a></li>
						<li><a href="AccountInfoServlet">Account Information</a></li>
						<li><a href="LogoutServlet">Log Out <i class="fa fa-user" aria-hidden="true"></i></a></li>
					</c:when>
					<c:otherwise>
						<li><a href="Home.jsp"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> Home</a>
						<li><a href="MenuItemServlet">Menu</a></li>
						<li><a href="Registration.jsp">Register <i class="fa fa-user-plus" aria-hidden="true"></i></a></li>
						<li><a href="Login.jsp">Login <i class="fa fa-user" aria-hidden="true"></i></a></li>
					</c:otherwise>
				</c:choose>
				</ul>
			</div>
		</div>
	</nav>