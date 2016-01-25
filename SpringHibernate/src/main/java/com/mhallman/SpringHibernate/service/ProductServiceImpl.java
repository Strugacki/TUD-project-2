package com.mhallman.SpringHibernate.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mhallman.SpringHibernate.model.Client;
import com.mhallman.SpringHibernate.model.Product;

@Component
@Transactional
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
		sessionFactory.getCurrentSession().save(product);
		
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
	@SuppressWarnings("unchecked")
	public List<Product> getAllProducts() {
		return sessionFactory.getCurrentSession().getNamedQuery("getAllProducts").list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Product> getAvailableProducts(){
		return sessionFactory.getCurrentSession().getNamedQuery("getAvailableProducts").list();
	}

	@Override
	public Product getProductById(Long id) {
		return (Product) sessionFactory.getCurrentSession().get(Product.class, id);
	}



	
	
	@Override
	public void disposeProduct(Client client, Product product) {
		client = (Client) sessionFactory.getCurrentSession().get(Client.class, client.getId());
		product = (Product) sessionFactory.getCurrentSession().get(Product.class, product.getId());

		Product toRemove = null;
		// lazy loading here (person.getCars)
		for (Product aProduct : client.getProducts())
			if (aProduct.getId().compareTo(product.getId()) == 0) {
				toRemove = aProduct;
				break;
			}
		if (toRemove != null)
			client.getProducts().remove(toRemove);
		product.setAvailable(false);
		
	}

	@Override
	public List<Product> getSoldProducts(Client client) {
		client = (Client) sessionFactory.getCurrentSession().get(Client.class, client.getId());
		
		List<Product> soldProducts = new ArrayList<Product>(client.getProducts());
		return soldProducts;
	}

	@Override
	public void sellProduct(Long clientId, Long productId) {
		Client client = (Client) sessionFactory.getCurrentSession().get(Client.class, clientId);
		Product product = (Product) sessionFactory.getCurrentSession().get(Product.class, productId);
		product.setAvailable(false);
		client.getProducts().add(product);
		
	}

}
