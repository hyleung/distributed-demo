package com.example.server;

import com.example.command.catalog.CatalogCommandFactory;
import com.example.command.inventory.InventoryCommandFactory;
import com.example.database.ProductCatalogDb;
import com.example.resource.CatalogResource;
import com.example.resource.ProductInfoResource;
import com.example.service.ProductCatalogService;
import com.example.service.ProductInventoryService;
import com.example.service.StoreAvailabilityService;
import com.example.tracing.BraveTracingDecorator;
import com.example.tracing.BraveTracingHandler;
import com.google.inject.AbstractModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.server.RatpackServer;

import static ratpack.guice.Guice.registry;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-29
 * Time: 6:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class App {
	private static Logger LOGGER = LoggerFactory.getLogger(App.class);
	public static void main(String[] args) throws Exception {
        final String contextPath = System.getProperty("jettyRun.contextPath","/");
        final int port = Integer.parseInt(System.getProperty("jettyRun.httpPort", "8080"));
		RatpackServer.start(s -> s
                .serverConfig(c -> c.port(port))
                .registry(registry(binding -> {
                    binding.module(BraveTraceModule.class);
                    binding.module(AppModule.class);
					binding.bind(BraveTracingHandler.class);
					binding.bind(BraveTracingDecorator.class);
                }))
				.handlers(handler -> handler
							.get("catalog", CatalogResource.class)
							.get("product/:id", ProductInfoResource.class)
							.all(ctx -> ctx.render("root")))
        );
	}

	public static class AppModule extends AbstractModule {
		@Override
		protected void configure() {
			bind(CatalogResource.class);
			bind(ProductInfoResource.class);
			bind(ProductCatalogService.class).asEagerSingleton();
			bind(ProductInventoryService.class).asEagerSingleton();
			bind(StoreAvailabilityService.class).asEagerSingleton();
			bind(ProductCatalogDb.class).asEagerSingleton();
			bind(CatalogCommandFactory.class).asEagerSingleton();
			bind(InventoryCommandFactory.class).asEagerSingleton();
		}
	}
}
