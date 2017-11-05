package com.salestock.didik.api.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ListData<T> implements Serializable {
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	private Integer page;
	private Integer size;
	private Integer totalPage;
	
	@JsonProperty(value="contents")
	@JsonInclude(value=Include.NON_NULL)
	private List<T> contents;
	
	public List<T> getContents() {
		return contents;
	}
	public void setContents(List<T> data) {
		this.contents = data;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
}
