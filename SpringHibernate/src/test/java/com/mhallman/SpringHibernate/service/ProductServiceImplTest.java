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
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
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
	
	private List<Product> productsToTest;
	private List<Client> clientsToTest;
	
			
	@Before
	public void fill(){
		productsToTest = new ArrayList<Product>();
		clientsToTest = new ArrayList<Client>();
		Product product = new Product(productName,brandName,price,available);
		ps.addProduct(product);
		productsToTest.add(product);
		Product product1 = new Product(productName1,brandName1,price1,available1);
		ps.addProduct(product1);
		productsToTest.add(product1);
		Client client = new Client(firstName,lastName,phoneNumber);
		cs.addClient(client);
		clientsToTest.add(client);
		Client client1 = new Client(firstName1,lastName1,phoneNumber1);
		cs.addClient(client1);
		clientsToTest.add(client1);
	}
	
	@After
	public void clean(){
		List<Product> products = new ArrayList<Product>();
		products.add(ps.getProductById(productsToTest.get(0).getId()));
		products.add(ps.getProductById(productsToTest.get(1).getId()));
		
		List<Client> clients = new ArrayList<Client>();
		clients.add(cs.getClientById(clientsToTest.get(0).getId()));
		clients.add(cs.getClientById(clientsToTest.get(1).getId()));
		
		for(Client client : clients){
			for(Product product : client.getProducts()){
				product.setAvailable(true);
				
			}
			client.getProducts().clear();
			cs.deleteClient(client);
		}
		for(Product productt : products){
			ps.deleteProduct(productt);
		}
	}
	
	
	@Test
	public void checkGetById(){
		
		Product byId = ps.getProductById(productsToTest.get(0).getId());
		
		assertEquals(productName,byId.getProductName());
		assertEquals(brandName,byId.getBrandName());
		assertEquals(available,byId.getAvailable());
	}
	
	@Test
	public void checkAdding(){
		List<Product> products = new ArrayList<Product>();
		products.add(ps.getProductById(productsToTest.get(0).getId()));
		products.add(ps.getProductById(productsToTest.get(1).getId()));
		Product toCheck = products.get(0);
		
		assertEquals(productName,toCheck.getProductName());
		assertEquals(brandName,toCheck.getBrandName());
		assertEquals(price,toCheck.getPrice(),0.00);//0.00 means tolerance between actual and expected value
		assertEquals(2,products.size());
	}
	
	@Test
	public void checkDeleting(){
		Product toDelete = new Product(productName1,brandName1,22.00,true);
		ps.addProduct(toDelete);
		
		Product toCheck = ps.getProductById(toDelete.getId());
		
		int size = ps.getAllProducts().size();
		assertEquals(productName1,toCheck.getProductName());
		assertEquals(brandName1,toCheck.getBrandName());
		
		ps.deleteProduct(toCheck);
		
		int afterDeleteSize = ps.getAllProducts().size();
		assertNotSame(size,afterDeleteSize);
		assertNull(ps.getProductById(toCheck.getId()));
	}
	
	
	@Test
	public void checkUpdating(){
		List<Product> products = new ArrayList<Product>();
		products.add(ps.getProductById(productsToTest.get(0).getId()));
		products.add(ps.getProductById(productsToTest.get(1).getId()));
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
		List<Product> products = new ArrayList<Product>();
		products.add(ps.getProductById(productsToTest.get(0).getId()));
		products.add(ps.getProductById(productsToTest.get(1).getId()));
		int size = products.size();
		assertEquals(size,2);
		assertEquals(productName,products.get(0).getProductName());
		assertEquals(productName1,products.get(1).getProductName());
	}
	
	@Test
	public void checkSellingAndAvailablity(){
		List<Product> products = new ArrayList<Product>();
		products.add(ps.getProductById(productsToTest.get(0).getId()));
		products.add(ps.getProductById(productsToTest.get(1).getId()));
		List<Client> clients = new ArrayList<Client>();
		clients.add(cs.getClientById(clientsToTest.get(0).getId()));
		clients.add(cs.getClientById(clientsToTest.get(1).getId()));
		
		Client toCheckClient = clients.get(0);
		Product toCheckProduct = products.get(0);
		
		//assertEquals(2,ps.getAllProducts().size());
		for(Product product : products){
			ps.sellProduct(toCheckClient.getId(), product.getId());
		}
		
		assertEquals(2,toCheckClient.getProducts().size());
		assertEquals(false,ps.getProductById(toCheckProduct.getId()).getAvailable());
		assertNotSame(ps.getAllProducts(),ps.getAvailableProducts());
	}
	
	@Test
	public void checkGettingBoughtAndDispose(){
		List<Product> products = new ArrayList<Product>();
		products.add(ps.getProductById(productsToTest.get(0).getId()));
		products.add(ps.getProductById(productsToTest.get(1).getId()));
		List<Client> clients = new ArrayList<Client>();
		clients.add(cs.getClientById(clientsToTest.get(0).getId()));
		clients.add(cs.getClientById(clientsToTest.get(1).getId()));
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
		assertEquals(true,toCheckClient.getProducts().isEmpty());
	}
}
