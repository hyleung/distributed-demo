package com.example.resource;

import com.example.domain.ProductInfo;
import com.example.domain.StoreAvailability;
import com.example.service.ProductCatalogService;
import com.example.service.ProductInventoryService;
import com.example.service.StoreAvailabilityService;
import com.google.inject.Inject;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static ratpack.jackson.Jackson.json;


/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-09
 * Time: 1:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductInfoResource implements Handler {
    private final ProductCatalogService catalogService;
    private final ProductInventoryService availabilityService;
    private final StoreAvailabilityService storeAvailabilityService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductInfoResource.class);

    @Inject
    public ProductInfoResource(final ProductCatalogService catalogService,
                               final ProductInventoryService availabilityService,
                               final StoreAvailabilityService storeAvailabilityService) {
        this.catalogService = catalogService;
        this.availabilityService = availabilityService;
        this.storeAvailabilityService = storeAvailabilityService;
    }


    private Optional<ProductInfo> retrieveInfo(String id, @QueryParam("storeAvailability") boolean checkStoreAvailability) {
        //init the Hystrix context (so that we can use request cache)
        HystrixRequestContext requestContext = HystrixRequestContext.initializeContext();
        try {
            ProductInfo productInfo = catalogService.retrieveProductInfo(id);
            Optional<Boolean> productAvailable = availabilityService.isProductAvailable(id);
            if (productAvailable.isPresent()) {
                productInfo.setInStock(productAvailable.get());
            }
            if (checkStoreAvailability) {
                Optional<List<StoreAvailability>> storeAvailabilityOption = storeAvailabilityService.retrieveStoreAvailability(id);
                if (storeAvailabilityOption.isPresent()) {
                    productInfo.setStoreAvailabilityList(storeAvailabilityOption.get());
                }
            }
            return Optional.of(productInfo);

        } catch (NoSuchElementException notFound) {
            return Optional.empty();
        } finally {
            requestContext.shutdown();
        }
    }

    @Override
    public void handle(Context ctx) throws Exception {
        String id = ctx.getPathTokens().get("id");
        boolean checkAvailability = Boolean.parseBoolean(ctx.getRequest()
                .getQueryParams().getOrDefault("storeAvailability", "false"));
        Optional<ProductInfo> optional = retrieveInfo(id, checkAvailability);
        if (optional.isPresent()) {
            ctx.render(json(optional.get()));
        } else {
            ctx.clientError(404);
        }
    }
}
