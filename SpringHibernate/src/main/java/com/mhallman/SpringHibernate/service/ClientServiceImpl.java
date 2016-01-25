package com.mhallman.SpringHibernate.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mhallman.SpringHibernate.model.Client;

@Transactional
@Component
public class ClientServiceImpl implements ClientService{


	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
	}
	
	@Override
	public void addClient(Client client){
		sessionFactory.getCurrentSession().persist(client);
	}
	
	
	@Override
	public void deleteClient(Client client){
		client = (Client) sessionFactory.getCurrentSession().get(Client.class, client.getId());
		sessionFactory.getCurrentSession().delete(client);
	}
	
	@Override
	public void updateClient(Client client){
		Client toUpdateClient = getClientById(client.getId());
		toUpdateClient.setFirstName(client.getFirstName());
		toUpdateClient.setLastName(client.getLastName());
		toUpdateClient.setPhoneNumber(client.getPhoneNumber());
		sessionFactory.getCurrentSession().update(toUpdateClient);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Client> getAllClients(){
		return sessionFactory.getCurrentSession().getNamedQuery("getAllClients").list();
	}
	
	@Override
	public Client getClientById(Integer id){
		return (Client) sessionFactory.getCurrentSession().get(Client.class, id);
	}
	
	@Override
	public Client getClientByPhone(String phone){
		return (Client) sessionFactory.getCurrentSession().getNamedQuery("getByPhone").setString(1, phone);
	}
	
}
