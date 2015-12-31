package com.mhallman.SpringHibernate.service;

import java.util.List;

import com.mhallman.SpringHibernate.model.Client;
import com.mhallman.SpringHibernate.model.Product;

public interface ProductService {

	void addProduct(Product product);
	void deleteProduct(Product product);
	void updateProduct(Product product);
	List<Product> getAllProducts(Product product);
	Product getProductById(Integer id);
	Product getProductByBrandName(String bName);
	
}
