package com.mhallman.SpringHibernate.service;

import com.mhallman.SpringHibernate.model.Client;

public interface ClientService {

	void addClient(Client client);
	void deleteClient(Client client);
	
}
