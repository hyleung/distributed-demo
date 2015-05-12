package com.example.service;

import com.example.domain.ProductInfo;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-10
 * Time: 8:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductInfoService {
	public ProductInfo retrieveProductInfo(String id) {
		return new ProductInfo(id);
	}
}
