package com.mhallman.SpringHibernate.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mhallman.SpringHibernate.model.Product;

public class ProductServiceImpl implements ProductService{

	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
	}
	
	@Override
	public void addProduct(Product product) {
		sessionFactory.getCurrentSession().persist(product);
		
	}

	@Override
	public void deleteProduct(Product product) {
		product = (Product) sessionFactory.getCurrentSession().get(Product.class, product.getId());
		sessionFactory.getCurrentSession().delete(product);
	}


	@Override
	public void updateProduct(Product product) {
		Product toUpdateProduct = getProductById(product.getId());
		toUpdateProduct.setProductName(product.getProductName());
		toUpdateProduct.setBrandName(product.getBrandName());
		toUpdateProduct.setPrice(product.getPrice());
		sessionFactory.getCurrentSession().update(toUpdateProduct);
		
	}


	@Override
	public List<Product> getAllProducts(Product product) {
		return sessionFactory.getCurrentSession().getNamedQuery("getAllProducts").list();
	}


	@Override
	public Product getProductById(Integer id) {
		return (Product) sessionFactory.getCurrentSession().get(Product.class, id);
	}


	@Override
	public Product getProductByBrandName(String bName) {
		return (Product) sessionFactory.getCurrentSession().getNamedQuery("getProductsByBrandName").setString(1, bName);
	}

}
