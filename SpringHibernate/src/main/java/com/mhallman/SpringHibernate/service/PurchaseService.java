package com.mhallman.SpringHibernate.service;

import java.util.List;

import com.mhallman.SpringHibernate.model.Client;
import com.mhallman.SpringHibernate.model.Product;
import com.mhallman.SpringHibernate.model.Purchase;

public interface PurchaseService {
	
	void addPurchase(Client client,Product product);
	List<Purchase> gettAllPurchases();
}
