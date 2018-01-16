<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Menu Item</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
	<script type="text/javascript" src="js/index.js"></script>
	<script src="https://use.fontawesome.com/71c97a3df8.js"></script>
</head>
<body>
<jsp:include page="header.jsp" />
	<!--If item was successfully added to the cart -->
	<c:if test="${sessionScope.cartIds != null && fn: length(sessionScope.cartIds) > 0}">
		<div class="container">
			<a href="StoreServlet">
				<button class="btn btn-lg btn-success" type="submit" name="process" id="process">Process Order</button>
			</a>
		</div>
	</c:if>
	<div class="container">
		<div class="jumbotron">
			<h2><i class="fa fa-cutlery" aria-hidden="true"></i> Menu Items</h2>
		</div>
		<!-- Display added to the cart message -->
		<!-- Set variable for ArrayList<Item> -->
		<c:set var="items" value="#{applicationScope.menuItems}"/>
		
		<!-- Test whether the items is empty or not. If items has nothing redirect to the MenuItemServlet -->
		<c:if test="${items.isEmpty()}">
			<c:redirect url="MenuItemServlet" />
		</c:if>
		
		<!-- Loop through the items list and display it as individual div's -->
		<div class="row">
			<c:forEach items="${items}" var="item">
				<div class="col-lg-4 col-sm-6 text-center">
					<div class="img-thumbnail">
						<div class="text-center">
							<a href="ItemServlet?itemId=${item.itemId}"><img src="${pageContext.request.contextPath}/images/${item.image}" width="300" height="300"></a>
						</div>
					</div>
					<div class="item-action">
						<p><strong>${item.itemName}</strong></p>
						<p><strong>Price: $${item.itemPrice}</strong></p>
					</div>
					<div class="item-operate">
						<!-- Only display if the user is logged in -->
						<c:if test="${sessionScope.userId != null}">
							<a href="ItemServlet?itemId=${item.itemId}">
								<button class="btn btn-sm btn-success" name="itemId" value="${item.itemId}" id="review-button">Reviews</button>
							</a>
							<button class="btn btn-sm btn-primary" data-toggle="modal" data-target="#myModal-${item.itemId}" name="itemId" value="${item.itemId}">Add To Cart</button>
						</c:if>
					</div>
				</div>
				<!-- Modal -->
				<div class="modal fade" id="myModal-${item.itemId}" role="dialog">
				    <div class="modal-dialog">
				    
				      <!-- Modal content-->
				      <div class="modal-content">
				        <div class="modal-header">
				          <button type="button" class="close" data-dismiss="modal">×</button>
				          <h4><span class="glyphicon glyphicon-lock"></span> Item</h4>
				        </div>
				        <div class="modal-body">
				          <form role="form" action="MenuItemServlet" method="post">
				            <div class="form-group">
				              <label for="psw"><span class="glyphicon glyphicon-shopping-cart"></span>$${item.itemPrice} Each</label>
				              <input type="number" class="form-control" id="psw" placeholder="How many?">
				            </div>
				              <button type="submit" class="btn btn-primary btn-block" name="itemId" value="${item.itemId}">Add 
				                <span class="glyphicon glyphicon-ok"></span>
				              </button>
				          </form>
				        </div>
				        <div class="modal-footer">
				          <button type="submit" class="btn btn-danger btn-default pull-left" data-dismiss="modal">
				            <span class="glyphicon glyphicon-remove"></span> Cancel
				          </button>
				          <p>Need <a href="#">help?</a></p>
				        </div>
				      </div>
				    </div>
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