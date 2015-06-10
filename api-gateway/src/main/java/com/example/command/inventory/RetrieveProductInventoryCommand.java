package com.example.command.inventory;

import com.example.command.CommandGroups;
import com.example.domain.ProductInventory;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-20
 * Time: 8:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class RetrieveProductInventoryCommand extends HystrixCommand<Optional<ProductInventory>> {
	private final String productId;
	private final String serviceHost;
	private final int servicePort;
	private static final Logger LOGGER = LoggerFactory.getLogger(RetrieveProductInventoryCommand.class);
	public RetrieveProductInventoryCommand(String productId) {
		super(Setter
				.withGroupKey(HystrixCommandGroupKey.Factory.asKey(CommandGroups.INVENTORY))
				.andCommandKey(HystrixCommandKey.Factory.asKey("Product Availability")));
		this.productId = productId;
		this.serviceHost = System.getProperty("inventory.service.host","http://localhost");
		this.servicePort = Integer.parseInt(System.getProperty("inventory.service.port","3000"));
	}

	@Override
	protected Optional<ProductInventory> run() throws Exception {
		Client client = ClientBuilder.newClient();
		String uri = String.format("%s:%d/api/availability/%s", serviceHost, servicePort, productId);
		LOGGER.info(uri);
		Invocation invocation =
				client.target(uri)
				.request()
				.buildGet();
		Response response = invocation.invoke();
		ProductInventory entity = response.readEntity(ProductInventory.class);
		return Optional.of(entity);
	}

	@Override
	protected Optional<ProductInventory> getFallback() {
		LOGGER.warn("Executing fallback");
		return Optional.empty();
	}
}
