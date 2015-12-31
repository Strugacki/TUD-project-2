package com.mhallman.SpringHibernate.service;

import com.mhallman.SpringHibernate.model.Product;

public interface ProductService {

	void addProduct(Product product);
	void deleteProduct(Product product);
	
	
}
