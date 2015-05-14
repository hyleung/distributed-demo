package com.example.resource;

import com.example.domain.ProductInfo;
import com.example.exception.ProductNotFoundException;
import com.example.service.ProductAvailabilityService;
import com.example.service.ProductCatalogService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.NoSuchElementException;


/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-09
 * Time: 1:52 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/product")
public class ProductInfoResource {
	private final ProductCatalogService catalogService = new ProductCatalogService();
	private final ProductAvailabilityService availabilityService = new ProductAvailabilityService();

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductInfo retrieveInfo(@PathParam("id") String id) {
		try {
			ProductInfo productInfo = catalogService.retrieveProductInfo(id);
			productInfo.setInStock(availabilityService.isProductAvailable(id));
			return productInfo;
		} catch (NoSuchElementException notFound) {
			throw new ProductNotFoundException();
		}
	}

}
