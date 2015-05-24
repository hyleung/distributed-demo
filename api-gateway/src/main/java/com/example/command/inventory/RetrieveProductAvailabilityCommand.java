package com.example.command.inventory;

import com.example.command.CommandGroups;
import com.example.domain.ProductAvailability;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

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
public class RetrieveProductAvailabilityCommand extends HystrixCommand<Optional<ProductAvailability>> {
	private final String productId;
	public RetrieveProductAvailabilityCommand(String productId) {
		super(Setter
				.withGroupKey(HystrixCommandGroupKey.Factory.asKey(CommandGroups.INVENTORY))
				.andCommandKey(HystrixCommandKey.Factory.asKey("Product Availability")));
		this.productId = productId;
	}

	@Override
	protected Optional<ProductAvailability> run() throws Exception {
		Client client = ClientBuilder.newClient();
		Invocation invocation = client.target("http://localhost:3000/api/availability/" + productId)
				.request()
				.buildGet();
		Response response = invocation.invoke();
		ProductAvailability entity = response.readEntity(ProductAvailability.class);
		return Optional.of(entity);
	}

	@Override
	protected Optional<ProductAvailability> getFallback() {
		return Optional.empty();
	}
}
