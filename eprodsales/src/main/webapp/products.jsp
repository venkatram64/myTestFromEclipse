<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
     <link rel="stylesheet" href="./css/bootstrap.min.css">
     <script src="./js/bootstrap.min.js"></script>
     <script src="./js/jquery_3_2_1.js"></script>
    <title>E-Products</title>
</head>
<body>
    <div class="container">       
        <div align="center">
        	<table class="table">
	            <tr>
	            	<caption><span><h2>List of Products</h2></span></caption>
	            </tr>
	        </table>
            <table class="table table-bordered table-striped table-bordered table-hover">                
                <tr>
                    <th>Name</th>
                    <th>Model Year</th>
                    <th>Price</th>
                    <th></th>
                </tr>
                <c:forEach var="prod" items="${products}">
                    <tr>
                        <td><c:out value="${prod.productName}" /></td>
                         <td><c:out value="${prod.modelYear}" /></td>
                        <td><c:out value="${prod.price}" /></td>
                        <td><a href="/eprodsales/checkout?id=<c:out value='${prod.productId}' />">Checkout</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</body>
</html>