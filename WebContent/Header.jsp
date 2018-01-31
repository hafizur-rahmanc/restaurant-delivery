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
				<a href="Home.jsp" class="navbar-brand" id="home">Restaurant Delivery</a>
			</div>
			<div class="collapse navbar-collapse" id="bs-nav-demo">
				<c:set var="userId" value="${sessionScope.userId}" />
				<ul class="nav navbar-nav navbar-right">
				<c:choose>
					<c:when test="${userId != null}">
						<li><a href="Home.jsp" id="home-link"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> Home</a></li>
						<li><a href="MenuItemServlet" id="menu-link">Menu</a></li>
						<li><a href="OrdersHistoryServlet" id="orders-link">View Orders</a></li>
						<li><a href="AccountInfoServlet" id="account-info">Account Information</a></li>
						<li><a href="LogoutServlet" id="logout">Log Out <i class="fa fa-user" aria-hidden="true"></i></a></li>
					</c:when>
					<c:otherwise>
						<li><a href="Home.jsp" id="home-link"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> Home</a>
						<li><a href="MenuItemServlet" id="menu-link">Menu</a></li>
						<li><a href="Registration.jsp" id="register-link">Register <i class="fa fa-user-plus" aria-hidden="true"></i></a></li>
						<li><a href="Login.jsp" id="login-link">Login <i class="fa fa-user" aria-hidden="true"></i></a></li>
					</c:otherwise>
				</c:choose>
				</ul>
			</div>
		</div>
	</nav>