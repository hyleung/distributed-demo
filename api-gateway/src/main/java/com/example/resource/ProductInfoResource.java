package com.example.resource;

import com.example.domain.ProductInfo;
import com.example.service.ProductAvailabilityService;
import com.example.service.ProductCatalogService;
import com.example.service.StoreAvailabilityService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
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
	private final StoreAvailabilityService storeAvailabilityService = new StoreAvailabilityService();

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveInfo(@PathParam("id") String id, @QueryParam("storeAvailability") boolean checkStoreAvailability) {
		try {
			ProductInfo productInfo = catalogService.retrieveProductInfo(id);
			productInfo.setInStock(availabilityService.isProductAvailable(id));
			if (checkStoreAvailability)
				productInfo.setStoreAvailabilityList(storeAvailabilityService.retrieveStoreAvailability(id));
			return Response
					.ok(productInfo)
					.build();
		} catch (NoSuchElementException notFound) {
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity("Product not found")
					.build();
		} catch (IOException ioError) {
			ioError.printStackTrace();
			return Response
						.serverError()
						.build();
		}
	}

}
