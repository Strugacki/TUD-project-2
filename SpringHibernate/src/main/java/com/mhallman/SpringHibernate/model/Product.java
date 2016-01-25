package com.mhallman.SpringHibernate.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@SuppressWarnings("serial")
@Entity
@NamedQueries({
	@NamedQuery(name = "getAllProducts", query = "Select p from Product p"),
	@NamedQuery(name = "getAvailableProducts", query = "Select p from Product p Where p.available=true"),
})
public class Product implements Serializable{

	
	private Long id;
	
	private String productName;
	
	private String brandName;
	
	private double price;

	private boolean available=true;
	

	public Product(){
		
	}
	
	public Product(String productName, String brandName, Double price, boolean available){
		super();
		this.productName=productName;
		this.brandName=brandName;
		this.price=price;
		this.available=available;
	}
	
	
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * @return the available
	 */
	public boolean getAvailable() {
		return available;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
}	
