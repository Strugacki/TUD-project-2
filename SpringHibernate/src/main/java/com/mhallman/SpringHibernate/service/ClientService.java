package com.mhallman.SpringHibernate.service;

import java.util.List;

import com.mhallman.SpringHibernate.model.Client;

public interface ClientService {

	void addClient(Client client);
	void deleteClient(Client client);
	void updateClient(Client client);
	List<Client> getAllClients();
	Client getClientById(Integer id);
	Client getClientByPhone(String phone);
}
