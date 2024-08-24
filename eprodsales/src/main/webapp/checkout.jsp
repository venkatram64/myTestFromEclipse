<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" href="./css/bootstrap.min.css">
<script src="./js/jquery_3_2_1.js"></script>
<script src="./js/bootstrap.min.js"></script>

<title>E-Products</title>
</head>
<body>
	<div class="container">
		<div align="center">
			<table class="table">
				<tr>
					<td><span>
							<h2>
								Buying
								<c:out value="${product.productName}" />
								of
								<c:out value="${product.price}" />
							</h2>
					</span></td>
					<td><a href="/eprodsales/cancel">Cancel</a></td>
				</tr>
			</table>

		</div>
		<div>
			<div tabindex="-1" id="userModal" role="dialog" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-body">
							<form class="form col-md-12 center-block" method="post"	action="order">
								<div class="form-group">
									<input class="form-control input-lg" type="hidden"
										value="${product.productName}" name="prodName">
								</div>
								<div class="form-group">
									<input class="form-control input-lg" type="hidden"
										value="${product.price}" name="price">
								</div>
								<div class="form-group">
									<input class="form-control input-lg" type="hidden"
										value="${product.productId}" name="productId">
								</div>
								<div class="form-group">
									<input class="form-control input-lg" type="text" name="name"
										placeholder="Name">
								</div>
								<div class="form-group">
									<input class="form-control input-lg" type="text" name="email"
										placeholder="Email">
								</div>
								<div class="form-group">
									<input class="form-control input-lg" type="text" name="line1"
										placeholder="Location">
								</div>
								<div class="form-group">
									<input class="form-control input-lg" type="text" name="city"
										placeholder="City">
								</div>
								<div class="form-group">
									<input class="form-control input-lg" type="text" name="state"
										placeholder="State">
								</div>
								<div class="form-group">
									<input class="form-control input-lg" type="text" name="zipCode"
										placeholder="Zip Code">
								</div>
								
								<div class="form-group">
									<label for="cardNumber">Card number</label>
									<div class="input-group">
										<input type="text" name="cardNumber"
											placeholder="Your card number" class="form-control" required>
										
									</div>
								</div>
								<div class="row">
									<div class="col-sm-8">
										<div class="form-group">
											<label><span class="hidden-xs">Expiration</span></label>
											<div class="input-group">
												<input type="number" placeholder="MM" name="month"	class="form-control" required> 
												<input type="number" placeholder="YY" name="year" class="form-control" required>
											</div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group mb-4">
											<label data-toggle="tooltip"
												title="Three-digits code on the back of your card">CVV												
											</label> <input type="text" required name="cvc" class="form-control">
										</div>
									</div>
								</div>
								<button class="btn btn-primary btn-lg btn-block">Confirm</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>
</html>