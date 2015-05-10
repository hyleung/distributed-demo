package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-09
 * Time: 1:52 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/product")
public class ProductInfoService {
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductInfo retrieveInfo(@PathParam("id") String id) {
		return new ProductInfo(id);
	}

	@SuppressWarnings("unused")
	public static class ProductInfo {

		public ProductInfo() {
		}

		public ProductInfo(String id) {
			this.id = id;
		}
		String id;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	}
}
