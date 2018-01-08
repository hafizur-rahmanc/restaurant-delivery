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
				<a href="home.jsp" class="navbar-brand"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> Restaurant Delivery</a>
			</div>
			<div class="collapse navbar-collapse" id="bs-nav-demo">
				<ul class="nav navbar-nav">
					<li><a href="menu-item.jsp">Menu</a></li>
					<li><a href="#">About</a></li>
					<li><a href="#">Contact</a></li>
				</ul>
				<c:set var="currentUser" value="${sessionScope.currentUser}" />
				<ul class="nav navbar-nav navbar-right">
				<c:choose>
					<c:when test="${currentUser != null}">
						<li><a href="LogoutServlet">Log Out <i class="fa fa-user" aria-hidden="true"></i></a></li>
					</c:when>
					<c:otherwise>
						<li><a href="registration.jsp">Sign Up <i class="fa fa-user-plus" aria-hidden="true"></i></a></li>
						<li><a href="login.jsp">Login <i class="fa fa-user" aria-hidden="true"></i></a></li>
					</c:otherwise>
				</c:choose>
				</ul>

			</div>
		</div>
	</nav>