package com.salestock.didik.controller;

import io.swagger.annotations.Api;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salestock.didik.api.response.ListData;
import com.salestock.didik.api.response.ApiResponse;
import com.salestock.didik.model.Product;
import com.salestock.didik.service.ProductService;

@RestController
@RequestMapping(value="/v1")
@Api(value="Product Api")
public class ProductController {

	private Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private ProductService productService;
	
	@GetMapping(value="/products", produces=MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<ListData<Product>> getAllProduct(
			@RequestParam(value="page", defaultValue="1") Integer page, 
			@RequestParam(value="size", defaultValue="100") Integer size, 
			@RequestParam(value="category_id", required=false) String categoryId,
			@RequestParam(value="sort", defaultValue="createDate", required=false) String sort, 
			@RequestParam(value="filter", required=false) String filter) {
		
		ApiResponse<ListData<Product>> response = new ApiResponse<ListData<Product>>();
		response.setMessage("Empty Product");
		response.setStatus("200");
		try {
			Page<Product> products = productService.getProducts((page > 0) ? page - 1 : page, 
					size, filter, categoryId, sort);
			if(products.getTotalElements() > 0){
				ListData<Product> responseData = new ListData<Product>();
				responseData.setContents(products.getContent());
				responseData.setPage(page);
				responseData.setSize(size);
				responseData.setTotalPage(products.getTotalPages());
				
				response.setData(responseData);
				response.setMessage("Success");
				response.setStatus("200");
				return response;
			}
		} catch (Exception e) {
			logger.fatal("getAllProduct", e);
			response.setStatus("500");
			response.setMessage(e.getMessage());
			return response;
		}
		return response;
	}
	
	@GetMapping(value="/products/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<Product> getProductDetails(@PathVariable(value="id", required=true) String productId){
		ApiResponse<Product> response = new ApiResponse<Product>();
		response.setMessage("Product Not Found");
		response.setStatus("404");
		try {
			Product product = productService.getProduct(productId);
			if(product != null){
				response.setMessage("Success");
				response.setStatus("200");
				response.setData(product);
				return response;
			}
		} catch (Exception e) {
			logger.fatal("getAllProduct", e);
			response.setStatus("500");
			response.setMessage(e.getMessage());
			return response;
		}
		return response;
	}
}
