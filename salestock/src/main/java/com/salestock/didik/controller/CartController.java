package com.salestock.didik.controller;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.Api;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salestock.didik.api.request.AddToCart;
import com.salestock.didik.api.request.UpdateCart;
import com.salestock.didik.api.response.ApiResponse;
import com.salestock.didik.api.response.CartResponse;
import com.salestock.didik.api.response.ListData;
import com.salestock.didik.api.response.ResponseBuilder;
import com.salestock.didik.model.ProductDetail;
import com.salestock.didik.model.ShoppingCart;
import com.salestock.didik.service.ProductService;
import com.salestock.didik.service.ShoppingCartService;

@RestController
@RequestMapping(value="/v1")
@Api(value="Cart Api")
public class CartController {

	private Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private ProductService productService;
	
	@GetMapping(value="carts", produces=MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<ListData<CartResponse>> getListCart(
			@RequestParam(value="page", defaultValue="1") Integer page, 
			@RequestParam(value="size", defaultValue="100") Integer size){
		
		try {
			ListData<CartResponse> responseData = listCart(page, size);
			if(responseData != null){
				return ResponseBuilder.responseSuccess("Success", responseData);
			}
			return ResponseBuilder.responseSuccess("Empty Cart", null);
		} catch (Exception e) {
			logger.fatal("getAllProduct", e);
			return ResponseBuilder.responseError(e.getMessage());
		}
	}

	private ListData<CartResponse> listCart(Integer page, Integer size) {
		ListData<CartResponse> response = new ListData<CartResponse>();
		Page<ShoppingCart> result = shoppingCartService.getShoppingCarts((page > 0) ? page - 1 : page, size);
		if(result.getTotalElements() > 0){
			List<CartResponse> responseData = new ArrayList<CartResponse>();
			for (ShoppingCart shoppingCart : result.getContent()) {
				Hibernate.initialize(shoppingCart.getProduct());
				Hibernate.initialize(shoppingCart.getProductDetail());
				CartResponse data = new CartResponse(shoppingCart);
				responseData.add(data);
			}
			response.setContents(responseData);
			response.setPage(page);
			response.setSize(size);
			response.setTotalPage(result.getTotalPages());
			return response;
		}
		return null;
	}
	
	@PostMapping(value="/add-to-cart", produces=MediaType.APPLICATION_JSON_VALUE, 
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<ListData<CartResponse>> addToCart(@Valid @RequestBody AddToCart requestData){
		
		try {
			ProductDetail productDetail = productService.getProductDetail(requestData.getOptionId());
			
			if(productDetail != null && productDetail.getStock() >= requestData.getQuantity()){
				
				shoppingCartService.addToCart(productDetail.getProduct(), productDetail, requestData.getQuantity());
				ListData<CartResponse> listData = listCart(1, 100);
				return ResponseBuilder.responseSuccess("Success add to your cart", listData);
			}
			
			return ResponseBuilder.responseError("Stock unavailable");
		} catch (Exception e) {
			logger.fatal("getAllProduct", e);
			return ResponseBuilder.responseError(e.getMessage());
		}
	}
	
	@PutMapping(value="update-cart", produces=MediaType.APPLICATION_JSON_VALUE, 
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<CartResponse> updateCart(@Valid @RequestBody UpdateCart requestData){
		
		try {
			ShoppingCart cartItem = shoppingCartService.getCartItem(requestData.getCartId());
			if(cartItem != null && cartItem.getProductDetail() != null){
				if(cartItem.getProductDetail().getStock() - requestData.getQuantity() >= 0){
					cartItem.setQuantity(requestData.getQuantity());
					ShoppingCart updatedCart = shoppingCartService.updateCart(cartItem);
					Hibernate.initialize(updatedCart.getProduct());
					Hibernate.initialize(updatedCart.getProductDetail());
					CartResponse response = new CartResponse(updatedCart);
					return ResponseBuilder.responseSuccess("Updated", response);
				}
				return ResponseBuilder.responseError("Stock unavailable");
			}
			return ResponseBuilder.responseError("Cart not Found");
		} catch (Exception e) {
			logger.fatal("getAllProduct", e);
			return ResponseBuilder.responseError(e.getMessage());
		}
	}
	
	@DeleteMapping(value="delete-cart/{id}")
	public ApiResponse<ListData<CartResponse>> deleteCartItem(@PathVariable(value="id") String cartId){
		try {
			boolean deleted = shoppingCartService.deleteCartItem(cartId);
			if(deleted){
				ListData<CartResponse> listData = listCart(1, 100);
				return ResponseBuilder.responseSuccess("Item deleted from your Cart", listData);
			}
		} catch (Exception e) {
			logger.fatal("getAllProduct", e);
			return ResponseBuilder.responseError(e.getMessage());
		}
		return ResponseBuilder.responseError("Item not deleted from your Cart");
	}
}
