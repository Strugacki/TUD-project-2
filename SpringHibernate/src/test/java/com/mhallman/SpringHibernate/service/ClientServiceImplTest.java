package com.mhallman.SpringHibernate.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

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
	
	@Before
	public void fill(){
		Client client = new Client(firstName,lastName,phoneNumber);
		cs.addClient(client);
		Client client1 = new Client(firstName1,lastName1,phoneNumber1);
		cs.addClient(client1);
	}
	
	@After
	public void clean(){
		List<Client> clients = cs.getAllClients();
		for(Client client : clients){
			cs.deleteClient(client);
		}
	}
	
	
	@Test
	public void checkAdding(){
		List<Client> clients = cs.getAllClients();
		Client toCheck = cs.getClientById(clients.get(0).getId());
		
		assertEquals(firstName,toCheck.getFirstName());
		assertEquals(lastName,toCheck.getLastName());
		assertEquals(phoneNumber,toCheck.getPhoneNumber());
	}
	
	@Test
	public void checkDeleting(){
		
		List<Client> clients = cs.getAllClients();
		Client toCheck = cs.getClientById(clients.get(0).getId());
		int size = cs.getAllClients().size();
		
		assertEquals(firstName,toCheck.getFirstName());
		assertEquals(lastName,toCheck.getLastName());
		assertEquals(phoneNumber,toCheck.getPhoneNumber());
		assertEquals(size,2);
		
		cs.deleteClient(toCheck);
		size = cs.getAllClients().size();
		assertEquals(size,1);
		assertNull(cs.getClientById(toCheck.getId()));
	}
	
	@Test
	public void checkGettingAll(){
		List<Client> clients = cs.getAllClients();
		int size = clients.size();
		assertEquals(size,2);
		assertEquals(firstName,clients.get(0).getFirstName());
		assertEquals(firstName1,clients.get(1).getFirstName());
	}
	
	@Test
	public void checkUpdating(){
		List<Client> clients = cs.getAllClients();
		Client toCheck = cs.getClientById(clients.get(0).getId());
		
		assertEquals(firstName,toCheck.getFirstName());
		assertEquals(lastName,toCheck.getLastName());
		assertEquals(phoneNumber,toCheck.getPhoneNumber());
		
		toCheck.setFirstName(firstName1);
		cs.updateClient(toCheck);
		assertEquals(firstName1,toCheck.getFirstName());
	}
	
	
}
