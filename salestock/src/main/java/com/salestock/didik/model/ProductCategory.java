package com.salestock.didik.model;

// Generated Nov 3, 2017 5:45:10 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ProductCategory generated by hbm2java
 */
@Entity
@Table(name = "product_category")
public class ProductCategory implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	@JsonIgnore
	private Set<Product> products = new HashSet<Product>(0);

	public ProductCategory() {
	}

	public ProductCategory(String id) {
		this.id = id;
	}

	public ProductCategory(String id, String name, Set<Product> products) {
		this.id = id;
		this.name = name;
		this.products = products;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 100)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", length = 250)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productCategory")
	public Set<Product> getProducts() {
		return this.products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

}
