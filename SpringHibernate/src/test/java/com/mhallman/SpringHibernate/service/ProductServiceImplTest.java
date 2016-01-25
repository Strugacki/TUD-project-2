package com.mhallman.SpringHibernate.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.mhallman.SpringHibernate.model.Client;
import com.mhallman.SpringHibernate.model.Product;
import com.mhallman.SpringHibernate.service.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class ProductServiceImplTest {

	@Autowired
	ProductService ps;
	
	private final String productName="Truck";
	private final String brandName="Tensor";
	private final double price=159.00;
	private final boolean available=true;
	
	private final String productName1="Blat";
	private final String brandName1="PlanB";
	private final double price1=219.00;
	private final boolean available1=true;
	
	@Autowired
	ClientService cs;
	
	private final String firstName = "Kristof";
	private final String lastName = "Krafczyk";
	private final String phoneNumber = "070088045";
	
	private final String firstName1 = "Jerzy";
	private final String lastName1 = "Urban";
	private final String phoneNumber1 ="123321123";
	
			
	@Before
	public void fill(){
		Product product = new Product(productName,brandName,price,available);
		ps.addProduct(product);
		Product product1 = new Product(productName1,brandName1,price1,available1);
		ps.addProduct(product1);
		Client client = new Client(firstName,lastName,phoneNumber);
		cs.addClient(client);
		Client client1 = new Client(firstName1,lastName1,phoneNumber1);
		cs.addClient(client1);
	}
	
	@After
	public void clean(){
		List<Product> products = ps.getAllProducts();
		List<Client> clients = cs.getAllClients();
		for(Client client : clients){
			for(Product product : client.getProducts()){
				product.setAvailable(true);
			}
			client.getProducts().clear();
			cs.deleteClient(client);
		}
	}
	
	
	@Test
	public void checkGetById(){
		
		Product product = new Product(productName,brandName,price,available);
		
		ps.addProduct(product);
		
		Product added = ps.getProductById(product.getId());
		
		assertEquals(productName,added.getProductName());
		assertEquals(brandName,added.getBrandName());
		assertEquals(available,added.getAvailable());
	}
	
	@Test
	public void checkAdding(){
		List<Product> products = ps.getAllProducts();
		Product toCheck = products.get(0);
		
		assertEquals(productName,toCheck.getProductName());
		assertEquals(brandName,toCheck.getBrandName());
		assertEquals(price,toCheck.getPrice(),0.00);//0.00 means tolerance between actual and expected value
		assertEquals(2,products.size());
	}
	
	@Test
	public void checkDeleting(){
		List<Product> products = ps.getAllProducts();
		Product toCheck = ps.getProductById(products.get(0).getId());
		int size = ps.getAllProducts().size();
		
		assertEquals(productName,toCheck.getProductName());
		assertEquals(brandName,toCheck.getBrandName());
		assertEquals(size,2);
		
		ps.deleteProduct(toCheck);
		size = ps.getAllProducts().size();
		assertEquals(size,1);
		assertNull(ps.getProductById(toCheck.getId()));
	}
	
	
	@Test
	public void checkUpdating(){
		List<Product> products = ps.getAllProducts();
		Product toCheck = ps.getProductById(products.get(0).getId());
		
		assertEquals(productName,toCheck.getProductName());
		assertEquals(brandName,toCheck.getBrandName());
		
		toCheck.setProductName(productName1);
		toCheck.setPrice(222.00);
		ps.updateProduct(toCheck);
		assertEquals(productName1,toCheck.getProductName());
		assertEquals(222.00,toCheck.getPrice(),0.00);
	}
	
	@Test
	public void checkGettingAll(){
		List<Product> products = ps.getAllProducts();
		int size = products.size();
		assertEquals(size,2);
		assertEquals(productName,products.get(0).getProductName());
		assertEquals(productName1,products.get(1).getProductName());
	}
	
	@Test
	public void checkSellingAndAvailablity(){
		List<Product> products = ps.getAllProducts();
		List<Client> clients = cs.getAllClients();
		
		Client toCheckClient = clients.get(0);
		Product toCheckProduct = products.get(0);
		
		assertEquals(2,ps.getAllProducts().size());
		toCheckClient.setProducts(products);
		for(Product product : products){
			product.setAvailable(false);
		}
		
		assertEquals(2,toCheckClient.getProducts().size());
		assertEquals(false,ps.getProductById(toCheckProduct.getId()).getAvailable());
		assertEquals(0,ps.getAvailableProducts().size());
	}
	
	@Test
	public void checkGettingBoughtAndDispose(){
		List<Product> products = ps.getAllProducts();
		List<Client> clients = cs.getAllClients();
		List<Product> boughtProducts;
		
		Client toCheckClient = clients.get(0);
		Product toSellProduct = products.get(0);
		
		ps.sellProduct(toCheckClient.getId(), toSellProduct.getId());
		boughtProducts = toCheckClient.getProducts();
		assertEquals(productName,boughtProducts.get(0).getProductName());
		assertEquals(brandName,boughtProducts.get(0).getBrandName());
		assertEquals(false,ps.getProductById(toSellProduct.getId()).getAvailable());
		
		ps.disposeProduct(toCheckClient, toSellProduct);
		assertNotSame(1,toCheckClient.getProducts());
		assertEquals(0,toCheckClient.getProducts());
	}
}
