package com.srijan.eapp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.srijan.eapp.model.Product;
import com.srijan.eapp.util.DBConnection;

public class ProductService {
	
	public boolean createProduct(Product product) throws ClassNotFoundException, SQLException {
		String insertSql = "insert into products(product_name, model_year, list_price) values (?, ?, ?)";
		
		DBConnection dbconn = DBConnection.getInstance();
		Connection conn = dbconn.getConnection();
		PreparedStatement st = conn.prepareStatement(insertSql);
		
		st.setString(1, product.getProductName());
		st.setInt(2,  product.getModelYear());
		st.setFloat(3, product.getPrice());
		return st.execute();
	}
	
	public Product getProductById(int prodId) throws ClassNotFoundException, SQLException {
		String selectSql = "select * from products where product_id = ?";
		
		DBConnection dbconn = DBConnection.getInstance();
		Connection conn = dbconn.getConnection();
		PreparedStatement st = conn.prepareStatement(selectSql);
		
		st.setInt(1, prodId);
		ResultSet rs = st.executeQuery();
		Product prod = new Product();
		if(rs.next()) {			
			prod.setProductId(rs.getInt("product_id"));
			prod.setModelYear(rs.getInt("model_year"));
			prod.setPrice(rs.getFloat("list_price"));
			prod.setProductName(rs.getString("product_name"));
		}
		return prod;
	}
	
	public List<Product> getAllProducts() throws ClassNotFoundException, SQLException {
		List<Product> products = new ArrayList<>();
		String selectSql = "select * from products";
		
		DBConnection dbconn = DBConnection.getInstance();
		Connection conn = dbconn.getConnection();
		PreparedStatement st = conn.prepareStatement(selectSql);
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			Product prod = new Product();
			prod.setProductId(rs.getInt("product_id"));
			prod.setModelYear(rs.getInt("model_year"));
			prod.setPrice(rs.getFloat("list_price"));
			prod.setProductName(rs.getString("product_name"));
			products.add(prod);
		}
		return products;
	}

}
