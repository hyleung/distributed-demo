package com.example.command.catalog;

import com.example.database.ProductCatalogDb;
import com.example.domain.ProductInfo;
import com.netflix.hystrix.HystrixCommand;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by hyleung on 2016-03-18.
 */
public class CatalogCommandFactory {
    private final ProductCatalogDb database;

    @Inject
    public CatalogCommandFactory(ProductCatalogDb database) {
        this.database = database;
    }

    public HystrixCommand<List<ProductInfo>> retriveCatalogCommand() {
        return new RetrieveCatalogCommand(database);
    }

    public HystrixCommand<ProductInfo> retrieveProductInfoCommand(final String id) {
        return new RetrieveProductInfoCommand(database, id);
    }
}
