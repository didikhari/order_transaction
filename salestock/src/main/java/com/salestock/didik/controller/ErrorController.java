package com.salestock.didik.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import springfox.documentation.annotations.ApiIgnore;

import com.salestock.didik.api.response.ApiResponse;
import com.salestock.didik.exception.RecordNotFoundException;
import com.salestock.didik.exception.ValidationErrorException;

@RestControllerAdvice
@ApiIgnore
public class ErrorController {

	@ExceptionHandler(value={Exception.class, RecordNotFoundException.class, ValidationErrorException.class})
	public ApiResponse<Void> recordNotFoundException(Exception exception){
		ApiResponse<Void> apiResponse = new ApiResponse<Void>();
		apiResponse.setStatus("error");
		apiResponse.setMessage(exception.getMessage());
		return apiResponse;
	}
}
