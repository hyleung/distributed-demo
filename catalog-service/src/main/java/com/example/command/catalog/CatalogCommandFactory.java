package com.example.command.catalog;

import com.example.database.ProductCatalogDb;
import com.example.domain.ProductInfo;
import com.github.kristofa.brave.LocalTracer;
import com.netflix.hystrix.HystrixCommand;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by hyleung on 2016-03-18.
 */
public class CatalogCommandFactory {
    private final ProductCatalogDb database;
    private final LocalTracer localTracer;
    @Inject
    public CatalogCommandFactory(final ProductCatalogDb database, final LocalTracer localTracer) {
        this.database = database;
        this.localTracer = localTracer;
    }

    public HystrixCommand<List<ProductInfo>> retriveCatalogCommand() {
        return new RetrieveCatalogCommand(database, localTracer);
    }

    public HystrixCommand<ProductInfo> retrieveProductInfoCommand(final String id) {
        return new RetrieveProductInfoCommand(database, id, localTracer);
    }
}
