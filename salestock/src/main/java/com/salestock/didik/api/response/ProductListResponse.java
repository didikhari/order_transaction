package com.salestock.didik.api.response;

import java.math.BigDecimal;

import com.salestock.didik.model.Product;
import com.salestock.didik.model.ProductCategory;

public class ProductListResponse {
	private String id;
	private String name;
	private BigDecimal price;
	private String imageUrl;
	private Category category;
	private String description;
	
	public ProductListResponse(Product product) {
		if(product != null){
			this.id = product.getId();
			this.name = product.getName();
			this.price = product.getPrice();
			this.imageUrl = product.getImageUrl();
			this.description = product.getDescription();
			this.category = new Category(product.getProductCategory());
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

class Category{
	private String id;
	private String name;
	
	public Category(ProductCategory category) {
		if(category != null){
			this.id = category.getId();
			this.name = category.getName();
		}
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}