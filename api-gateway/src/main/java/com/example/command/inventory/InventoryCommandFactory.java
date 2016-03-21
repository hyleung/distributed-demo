package com.example.command.inventory;

import com.example.domain.ProductInventory;
import com.example.domain.StoreAvailability;
import com.netflix.hystrix.HystrixCommand;

import java.util.List;
import java.util.Optional;

/**
 * Created by hyleung on 2016-03-20.
 */
public class InventoryCommandFactory {
    public HystrixCommand<Optional<ProductInventory>> retrieveProductInventory(final String productId) {
        return new RetrieveProductInventoryCommand(productId);
    }

    public HystrixCommand<Optional<List<StoreAvailability>>> retrieveStoreAvailability(final String productId) {
        return new RetrieveStoreAvailabilityCommand(productId);
    }
}
