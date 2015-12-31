package com.mhallman.SpringHibernate.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@SuppressWarnings("serial")
@Entity
@Table(name = "CLIENT")
@NamedQueries({
	@NamedQuery(name = "getAllClients", query = "Select c from Client c"),
	@NamedQuery(name = "getByPhone", query = "Select c from Client c Where c.phone=?"),
})
public class Client implements Serializable{
	
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME") 
	private String lastName;
	
	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;

	/**
	 * Constructor for Client
	 */
	public Client(){
		
	}
	
	/**
	 * Constructor for Client with params
	 * @param firstName
	 * @param lastName
	 * @param phoneNumber
	 */
	public Client(String firstName, String lastName, String phoneNumber){
		super();
		this.firstName=firstName;
		this.lastName=lastName;
		this.phoneNumber=phoneNumber;
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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}
