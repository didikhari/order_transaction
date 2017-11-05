package com.salestock.didik.api.response;

import com.salestock.didik.helper.Constant;

public class ResponseBuilder {

	public static <T> ApiResponse<T> responseError(String message){
		ApiResponse<T> response = new ApiResponse<T>();
		response.setMessage(message);
		response.setStatus(Constant.ERROR);
		return response;
	}
	
	public static <T> ApiResponse<T> responseSuccess(String message, T data){
		ApiResponse<T> response = new ApiResponse<T>();
		response.setMessage(message);
		response.setData(data);
		response.setStatus(Constant.SUCCESS);
		return response;
	}
}
