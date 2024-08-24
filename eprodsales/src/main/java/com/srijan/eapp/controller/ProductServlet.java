package com.srijan.eapp.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.srijan.eapp.model.Card;
import com.srijan.eapp.model.Charge;
import com.srijan.eapp.model.Product;
import com.srijan.eapp.service.ProductService;
import com.srijan.eapp.service.StripeService;
import com.stripe.exception.StripeException;

public class ProductServlet extends HttpServlet{
	
	private ProductService productService;
	
	public void init() {
		productService = new ProductService();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doGet(req, res);
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		String action = req.getServletPath();
		try {
			if(action.equalsIgnoreCase("/list_products")) {
				listProducts(req, res);
			}else if(action.contains("/checkout")) {
				checkout(req, res);
			}else if(action.contains("/cancel") || action.contains("/home")) {
				listProducts(req, res);
			}else if(action.contains("/order")) {
				buyProduct(req, res);
			}
		}catch(SQLException ex) {
			throw new ServletException(ex);
		}catch(ClassNotFoundException cnfe) {
			throw new ServletException(cnfe);
		}catch(StripeException e) {
			throw new ServletException(e);
		}
	}
	
	private void listProducts(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException, ClassNotFoundException, SQLException{
		List<Product> prods = productService.getAllProducts();
		req.setAttribute("products", prods);
		RequestDispatcher dispatcher = req.getRequestDispatcher("products.jsp");
		dispatcher.forward(req, res);
	}
	
	private void checkout(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException, ClassNotFoundException, SQLException{
		int id = Integer.parseInt(req.getParameter("id"));
		Product prod = productService.getProductById(id);
		req.setAttribute("product", prod);
		RequestDispatcher dispatcher = req.getRequestDispatcher("checkout.jsp");
		dispatcher.forward(req, res);
	}
	private void buyProduct(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException, ClassNotFoundException, SQLException, StripeException{
		int id = Integer.parseInt(req.getParameter("productId"));
		Product prod = productService.getProductById(id);
		req.setAttribute("product", prod);
		Charge charge = new Charge();
		
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String location = req.getParameter("line1");
		String city = req.getParameter("city");
		String state = req.getParameter("state");
		String zipCode = req.getParameter("zipCode");
		charge.setAmount((int)prod.getPrice());
		charge.setCurrency(Charge.Currency.INR);
		charge.setDescription(prod.getProductName());
		charge.setStripeEmail(email);
		Card card = new Card();
		String cardNumber = req.getParameter("cardNumber");
		String expMonth = req.getParameter("month");
		String expYear = req.getParameter("year");
		String cvc = req.getParameter("cvc");
		card.setCardNumber(cardNumber);
		card.setExpYear(expYear);
		card.setExpMonth(expMonth);
		card.setCvc(cvc);
		
		StripeService stipeService = new StripeService("sk_test_NtMJtvXWCYs8mKUl6ZPcPydT00uwyEiWFo");
		com.stripe.model.Charge stripeCharge = stipeService.chargeByToken(card, charge);
		System.out.println(stripeCharge);
		RequestDispatcher dispatcher = req.getRequestDispatcher("order.jsp");
		dispatcher.forward(req, res);
	}
}
