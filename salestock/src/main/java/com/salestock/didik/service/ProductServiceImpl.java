package com.salestock.didik.service;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.salestock.didik.model.Product;
import com.salestock.didik.model.ProductDetail;
import com.salestock.didik.model.QProduct;
import com.salestock.didik.repository.ProductDetailRespository;
import com.salestock.didik.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;
	private ProductDetailRespository productDetailRepository;
	
	@Autowired
	public ProductServiceImpl(ProductRepository productRepository, 
			ProductDetailRespository productDetailRespository) {
		this.productRepository = productRepository;
		this.productDetailRepository = productDetailRespository;
	}
	
	@Override
	public Page<Product> getProducts(Integer page, Integer size, String filter, 
			String categoryId, String sort){
		
		Pageable pageable = new PageRequest(page, size, new Sort(sort));		
		QProduct productQuery_ = QProduct.product;
		BooleanBuilder criteriaBuilder = new BooleanBuilder();
		
		if(StringUtils.isNotBlank(filter)){
			BooleanExpression nameLike = productQuery_.name.likeIgnoreCase("%"+filter+"%");
			criteriaBuilder.and(nameLike);
		}
		
		if(StringUtils.isNotBlank(categoryId)) {
			BooleanExpression withCategory = productQuery_.productCategory.id.eq(categoryId);
			criteriaBuilder.and(withCategory);
		}
		
		Page<Product> result = productRepository.findAll(criteriaBuilder, pageable);
		return result;
	}
	
	@Override
	public Product getProduct(String id){
		return productRepository.findOne(id);
	}

	@Override
	public ProductDetail getProductDetail(String detailId) {
		ProductDetail result = productDetailRepository.findOne(detailId);
		Hibernate.initialize(result.getProduct());
		return result;
	}
}
