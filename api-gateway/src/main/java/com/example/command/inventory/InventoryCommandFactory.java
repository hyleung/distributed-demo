package com.example.command.inventory;

import com.example.domain.ProductInventory;
import com.example.domain.StoreAvailability;
import com.github.kristofa.brave.jaxrs2.BraveClientRequestFilter;
import com.github.kristofa.brave.jaxrs2.BraveClientResponseFilter;
import com.google.inject.Inject;
import com.netflix.hystrix.HystrixCommand;

import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseFilter;
import java.util.List;
import java.util.Optional;

/**
 * Created by hyleung on 2016-03-20.
 */
public class InventoryCommandFactory {
    private final BraveClientRequestFilter requestFilter;
    private final BraveClientResponseFilter responseFilter;

    @Inject
    public InventoryCommandFactory(final BraveClientRequestFilter requestFilter,
                                   final BraveClientResponseFilter responseFilter) {
        this.requestFilter = requestFilter;
        this.responseFilter = responseFilter;
    }

    public HystrixCommand<Optional<ProductInventory>> retrieveProductInventory(final String productId) {
        return new RetrieveProductInventoryCommand(productId, requestFilter, responseFilter);
    }

    public HystrixCommand<Optional<List<StoreAvailability>>> retrieveStoreAvailability(final String productId) {
        return new RetrieveStoreAvailabilityCommand(productId, requestFilter, responseFilter);
    }
}
