<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
					<td><span>
							<h2>
								<c:out value="${product.productName}" />
								of
								<c:out value="${product.price}" />
								Will be delivered soon.
							</h2>
					</span></td>
					<td><a href="/eprodsales/home">Home</a></td>
				</tr>
			</table>

		</div>
	</div>
</body>
</html>