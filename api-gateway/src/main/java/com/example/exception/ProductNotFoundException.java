package com.example.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-13
 * Time: 10:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductNotFoundException extends WebApplicationException {
	public ProductNotFoundException() {
		super(Response
				.status(Response.Status.NOT_FOUND)
				.entity("Product not found")
				.build());
	}
}
