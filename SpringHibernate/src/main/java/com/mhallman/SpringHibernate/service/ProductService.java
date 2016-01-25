package com.mhallman.SpringHibernate.service;

import java.util.List;

import com.mhallman.SpringHibernate.model.Client;
import com.mhallman.SpringHibernate.model.Product;

public interface ProductService {

	void addProduct(Product product);
	void deleteProduct(Product product);
	void updateProduct(Product product);
	List<Product> getAllProducts();
	List<Product> getAvailableProducts();
	Product getProductById(Integer id);
	List<Product> getProductByBrandName(String bName);
	
	void disposeProduct(Client client, Product product);
	List<Product> getSoldProducts(Client client);
	void sellProduct(Long clientId, Long productId);
	
}
