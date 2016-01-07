package com.mhallman.SpringHibernate.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="PURCHASE")
@NamedQueries({
	@NamedQuery(name = "getAllPurchases", query = "Select p from Purchase p"),
})
public class Purchase implements Serializable{
    
	
	@Id
	@Column(name="Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="DATE")
	private String date;
	
	@Column(name = "ID_CLIENT")
	private Integer idClient;

	/**
	 * Constructor for Purchase
	 */
	public Purchase(){
		
	}
	
	/**
	 * Constructor for Purchase with params
	 * @param date
	 * @param idClient
	 */
	public Purchase(String date, Integer idClient){
		super();
		this.date=date;
		this.idClient=idClient;
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the idClient
	 */
	public Integer getIdClient() {
		return idClient;
	}

	/**
	 * @param idClient the idClient to set
	 */
	public void setIdClient(Integer idClient) {
		this.idClient = idClient;
	}
	
}
