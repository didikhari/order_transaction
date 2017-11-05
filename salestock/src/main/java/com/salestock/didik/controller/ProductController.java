package com.salestock.didik.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.salestock.didik.api.response.ProductListResponse;
import com.salestock.didik.api.response.ProductSingleResponse;
import com.salestock.didik.api.response.ResponseBuilder;
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
	public ApiResponse<ListData<ProductListResponse>> getAllProduct(
			@RequestParam(value="page", defaultValue="1") Integer page, 
			@RequestParam(value="size", defaultValue="100") Integer size, 
			@RequestParam(value="category_id", required=false) String categoryId,
			@RequestParam(value="sort", defaultValue="createDate", required=false) String sort, 
			@RequestParam(value="filter", required=false) String filter) {
		
		try {
			Page<Product> products = productService.getProducts((page > 0) ? page - 1 : page, 
					size, filter, categoryId, sort);
			if(products.getTotalElements() > 0){
				ListData<ProductListResponse> responseData = new ListData<ProductListResponse>();
				
				List<ProductListResponse> datas = new ArrayList<ProductListResponse>();
				List<Product> content = products.getContent();
				for (Product product : content) {
					ProductListResponse data = new ProductListResponse(product);
					datas.add(data);
				}
				
				responseData.setContents(datas);
				responseData.setPage(page);
				responseData.setSize(size);
				responseData.setTotalPage(products.getTotalPages());
				
				return ResponseBuilder.responseSuccess("Success", responseData);
			}
		} catch (Exception e) {
			logger.fatal("getAllProduct", e);
			return ResponseBuilder.responseError(e.getMessage());
		}
		return ResponseBuilder.responseSuccess("Empty Products", null);
	}
	
	@GetMapping(value="/products/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<ProductSingleResponse> getProductDetails(
			@PathVariable(value="id", required=true) String productId){
		try {
			Product product = productService.getProduct(productId);
			if(product != null){
				ProductSingleResponse response = new ProductSingleResponse(product);
				return ResponseBuilder.responseSuccess("Success", response);
			}
		} catch (Exception e) {
			logger.fatal("getAllProduct", e);
			ResponseBuilder.responseError(e.getMessage());
		}
		return ResponseBuilder.responseError("Product Not Found");
	}
}
