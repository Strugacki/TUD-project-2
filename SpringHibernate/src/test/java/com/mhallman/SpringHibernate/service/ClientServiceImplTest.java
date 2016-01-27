package com.mhallman.SpringHibernate.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import com.mhallman.SpringHibernate.service.ClientService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class ClientServiceImplTest {

	@Autowired
	ClientService cs;
	
	private final String firstName = "Kristof";
	private final String lastName = "Krafczyk";
	private final String phoneNumber = "070088045";
	
	private final String firstName1 = "Jerzy";
	private final String lastName1 = "Urban";
	private final String phoneNumber1 ="123321123";
	
	private List<Client> clientsToTest;
	
	@Before
	public void fill(){
		clientsToTest = new ArrayList<Client>();
		Client client = new Client(firstName,lastName,phoneNumber);
		cs.addClient(client);
		clientsToTest.add(client);
		Client client1 = new Client(firstName1,lastName1,phoneNumber1);
		cs.addClient(client1);
		clientsToTest.add(client1);
	}
	
	@After
	public void clean(){
		List<Client> clients = new ArrayList<Client>();
		clients.add(cs.getClientById(clientsToTest.get(0).getId()));
		clients.add(cs.getClientById(clientsToTest.get(1).getId()));
		for(Client client : clients){
			cs.deleteClient(client);
		}
	}
	
	
	@Test
	public void checkAdding(){
		List<Client> clients = new ArrayList<Client>();
		clients.add(cs.getClientById(clientsToTest.get(0).getId()));
		clients.add(cs.getClientById(clientsToTest.get(1).getId()));
		Client toCheck = cs.getClientById(clients.get(0).getId());
		
		assertEquals(firstName,toCheck.getFirstName());
		assertEquals(lastName,toCheck.getLastName());
		assertEquals(phoneNumber,toCheck.getPhoneNumber());
	}
	
	@Test
	public void checkDeleting(){
		
		Client toDelete = new Client(firstName1,lastName1,phoneNumber1);
		cs.addClient(toDelete);
		
		Client toCheck = cs.getClientById(toDelete.getId());
		
		int size = cs.getAllClients().size();
		assertEquals(firstName1,toCheck.getFirstName());
		assertEquals(lastName1,toCheck.getLastName());
		
		cs.deleteClient(toCheck);
		
		int afterDeleteSize = cs.getAllClients().size();
		assertNotSame(size,afterDeleteSize);
		assertNull(cs.getClientById(toCheck.getId()));
	}
	
	@Test
	public void checkGettingAll(){
		List<Client> clients = new ArrayList<Client>();
		clients.add(cs.getClientById(clientsToTest.get(0).getId()));
		clients.add(cs.getClientById(clientsToTest.get(1).getId()));
		int size = cs.getAllClients().size();
		assertNotNull(size);
		assertEquals(firstName,clients.get(0).getFirstName());
		assertEquals(firstName1,clients.get(1).getFirstName());
	}
	
	@Test
	public void checkUpdating(){
		List<Client> clients = new ArrayList<Client>();
		clients.add(cs.getClientById(clientsToTest.get(0).getId()));
		clients.add(cs.getClientById(clientsToTest.get(1).getId()));
		Client toCheck = cs.getClientById(clients.get(0).getId());
		
		assertEquals(firstName,toCheck.getFirstName());
		assertEquals(lastName,toCheck.getLastName());
		assertEquals(phoneNumber,toCheck.getPhoneNumber());
		
		toCheck.setFirstName(firstName1);
		cs.updateClient(toCheck);
		assertEquals(firstName1,toCheck.getFirstName());
	}
	
	
}
