package com.salestock.didik.api.response;

import java.util.ArrayList;
import java.util.List;

import com.salestock.didik.model.Product;
import com.salestock.didik.model.ProductDetail;

public class ProductSingleResponse extends ProductListResponse {
	private Double weight;
	private List<Detail> options = new ArrayList<Detail>();

	public ProductSingleResponse(Product product) {
		super(product);

		if (product != null) {
			this.weight = product.getWeight();
			if (product.getProductDetails() != null
					&& product.getProductDetails().size() > 0) {
				for (ProductDetail productDetail : product.getProductDetails()) {
					Detail detail = new Detail(productDetail);
					options.add(detail);
				}

			}
		}
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public List<Detail> getOptions() {
		return options;
	}

	public void setOptions(List<Detail> options) {
		this.options = options;
	}

}

class Detail {
	private String id;
	private String color;
	private String size;
	private Integer stock;

	public Detail(ProductDetail productDetail) {
		if (productDetail != null) {
			this.id = productDetail.getId();
			this.color = productDetail.getColor();
			this.size = productDetail.getSize();
			this.stock = productDetail.getStock();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
}