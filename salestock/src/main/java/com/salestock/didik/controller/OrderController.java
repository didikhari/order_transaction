package com.salestock.didik.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.salestock.didik.api.request.CreateAddressRequest;
import com.salestock.didik.api.request.OrderRequest;
import com.salestock.didik.api.request.UpdateOrder;
import com.salestock.didik.api.response.ApiResponse;
import com.salestock.didik.api.response.AddressResponse;
import com.salestock.didik.api.response.ListData;
import com.salestock.didik.api.response.PaymentConfirmResponse;
import com.salestock.didik.api.response.ResponseBuilder;
import com.salestock.didik.helper.CommonUtils;
import com.salestock.didik.helper.Constant;
import com.salestock.didik.model.Coupon;
import com.salestock.didik.model.OrderTransaction;
import com.salestock.didik.model.PaymentConfirmLog;
import com.salestock.didik.model.QShippingAddress;
import com.salestock.didik.model.ShippingAddress;
import com.salestock.didik.processor.model.OrderListResponse;
import com.salestock.didik.processor.model.OrderSingleResponse;
import com.salestock.didik.repository.CouponRepository;
import com.salestock.didik.repository.ShippingAddressRepository;
import com.salestock.didik.service.OrderService;

@RestController
@RequestMapping(value="/v1")
public class OrderController {
	private Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private ShippingAddressRepository shippingAddressRepository;
	@Autowired
	private CouponRepository couponRepository;
	
	@Value("${upload.folder}")
    private String uploadFolder;
	@Value("${image.url}")
	private String imagePath;
    
    /**
     * Add new Address
     * @param requestData
     * @return
     */
    @PostMapping(value="new-address", 
    		produces=MediaType.APPLICATION_JSON_VALUE, 
    		consumes=MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<AddressResponse> addShippingAddress(@Valid @RequestBody 
    		CreateAddressRequest requestData, Principal principal){
    	
    	ShippingAddress shippingAddress = new ShippingAddress(CommonUtils.generateUUID(), 
    			requestData.getFullName(), requestData.getEmail(), requestData.getPhone(), 
    			requestData.getAddressLine(), requestData.getSubDistrict(), requestData.getCity(), 
    			requestData.getProvince(), requestData.getPostalCode());
    	shippingAddress.setCityId(requestData.getCityId());
    	shippingAddress.setUserId(principal.getName());
    	
    	ShippingAddress address = shippingAddressRepository.save(shippingAddress);
    	AddressResponse response = new AddressResponse(address);
    	
    	return ResponseBuilder.responseSuccess("OK", response);
    }
    
    /**
     * List address of logged in User
     * @param principal
     * @return
     */
    @GetMapping(value="list-address", produces=MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<ListData<AddressResponse>> listUserAddress(Principal principal){
    	
    	QShippingAddress shippingAddress = QShippingAddress.shippingAddress;
    	BooleanExpression withUser = shippingAddress.userId.eq(principal.getName());
    	
    	List<AddressResponse> responseDatas = new ArrayList<AddressResponse>();
    	Iterable<ShippingAddress> addressList = shippingAddressRepository.findAll(withUser);
    	
    	for (ShippingAddress address : addressList) {
    		AddressResponse response = new AddressResponse(address);
    		responseDatas.add(response);
		}
    	
    	ListData<AddressResponse> response = new ListData<AddressResponse>();
    	response.setPage(1);
    	response.setTotalPage(1);
    	response.setSize(responseDatas.size());
    	response.setContents(responseDatas);
    	
    	return ResponseBuilder.responseSuccess("OK", response);
    }
    
    /**
     * Submit Order
     * @param requestData
     * @return
     */
	@PostMapping(value="submit-order", 
			produces=MediaType.APPLICATION_JSON_VALUE, 
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<OrderSingleResponse> submitOrder(@Valid @RequestBody OrderRequest requestData,
			Principal principal){
		
		try {
			Coupon coupon = null;
			if(StringUtils.isNotBlank(requestData.getCouponCode())){
				coupon = orderService.updateCouponStock(requestData.getCouponCode());
			}
			OrderTransaction orderTransaction = orderService.submitOrder(requestData, coupon, principal.getName());
			OrderSingleResponse response = new OrderSingleResponse(orderTransaction);
			return ResponseBuilder.responseSuccess("Success", response);
		} catch (Exception e) {
			logger.fatal("submit order failed", e);
			return ResponseBuilder.responseError(e.getMessage());
		}
	}
	
	@GetMapping(value="orders", produces=MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<ListData<OrderListResponse>> listOrder(
			@RequestParam(value="page", defaultValue="1") Integer page, 
			@RequestParam(value="size", defaultValue="100") Integer size, 
			@RequestParam(value="status", defaultValue=Constant.ORDER_INITIALIZED) String status,
			Principal principal){
		
		Page<OrderTransaction> responseData = orderService.listOrderTransaction(page, size, status, principal.getName());
		if(responseData != null && responseData.getTotalElements() > 0){
			List<OrderListResponse> contents = new ArrayList<OrderListResponse>();
			for (OrderTransaction orderTransaction : responseData.getContent()) {
				OrderListResponse order = new OrderListResponse(orderTransaction);
				contents.add(order);
			}
			ListData<OrderListResponse> listData = new ListData<OrderListResponse>();
			listData.setContents(contents);
			listData.setPage(page);
			listData.setSize(size);
			listData.setTotalPage(responseData.getTotalPages());
			return ResponseBuilder.responseSuccess("Success", listData);
		}
		return ResponseBuilder.responseSuccess("Order Empty", null);
	}
	
	@GetMapping(value="orders/{order_id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<OrderSingleResponse> orderDetails(@PathVariable(value="order_id") String orderId){
		OrderTransaction orderTransaction = orderService.getOrderById(orderId);
		if(orderTransaction != null){
			OrderSingleResponse response = new OrderSingleResponse(orderTransaction);
			return ResponseBuilder.responseSuccess("Success", response);
		}
		return ResponseBuilder.responseError("Order Not Found");
	}
	
	@PutMapping(value="/admin/update-order")
	public ApiResponse<OrderSingleResponse> updateOrder(@Valid @RequestBody UpdateOrder requestData, Principal principal){
		try {
			OrderTransaction orderTransaction = orderService.updateOrder(requestData.getOrderId(), 
					requestData.getStatus(), requestData.getTrackingCode(), principal.getName());
			
			if(orderTransaction != null){
				OrderSingleResponse response = new OrderSingleResponse(orderTransaction);
				return ResponseBuilder.responseSuccess("Success", response);
			}
		} catch (Exception e) {
			logger.fatal("update order failed", e);
			return ResponseBuilder.responseError("Update Failed");
		}
		return ResponseBuilder.responseError("Update Failed");
	}
	

	@PostMapping(value={"confirm-payment"}, consumes=MediaType.MULTIPART_FORM_DATA_VALUE, 
			 produces=MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse<PaymentConfirmResponse> submitPaymentProof(
			@RequestParam(value="order_id") String orderId, 
			@RequestParam(value="amount") String amount, 
			@RequestParam(value="sender_name") String senderName, 
			@RequestParam(value="dest_number") String receipentAccountNumber, 
			@RequestParam(value="file") MultipartFile file, Principal principal) throws Exception{
		 
		 OrderTransaction orderTransaction = orderService.getOrderById(orderId);
		 
		 if(orderTransaction != null){

			 if(!StringUtils.equals(orderTransaction.getUserId(), principal.getName()))
					throw new Exception("Access Denied");
			 
			 if(!orderTransaction.getTotalPrice().equals(new BigDecimal(amount)))
				 throw new Exception("Invalid Amount");
			 
			 PaymentConfirmLog confirmLog = new PaymentConfirmLog();
			 confirmLog.setId(CommonUtils.generateUUID());
			 confirmLog.setConfirmDate(CommonUtils.getCurrentDateTime());
			 confirmLog.setOrderTransaction(orderTransaction);
			 confirmLog.setReceipentAccountNumber(receipentAccountNumber);
			 confirmLog.setSenderName(senderName);
			 
			 if(file != null && StringUtils.isNotBlank(file.getOriginalFilename())){
				try {
					String fullPath = StringUtils.join(uploadFolder, orderId, 
							FilenameUtils.EXTENSION_SEPARATOR_STR, FilenameUtils.getExtension(file.getOriginalFilename()));
			    	File targetFile = new File(fullPath);
					FileUtils.writeByteArrayToFile(targetFile, file.getBytes());
					String urlPath = StringUtils.join(imagePath, orderId, FilenameUtils.EXTENSION_SEPARATOR_STR, 
							FilenameUtils.getExtension(file.getOriginalFilename()));
					confirmLog.setTransferReceiptUrl(urlPath);
					
				} catch (IOException e) {
					logger.error("Error Write File", e);
					throw new Exception("Upload Image Failed");
				}
			 }
			 
			 PaymentConfirmLog confirmation = orderService.saveConfirmation(confirmLog);
			 PaymentConfirmResponse response = new PaymentConfirmResponse(confirmation, orderId);
			 return ResponseBuilder.responseSuccess("Success", response);
		 }
		 return ResponseBuilder.responseError("Order Not Found");
	}
}
