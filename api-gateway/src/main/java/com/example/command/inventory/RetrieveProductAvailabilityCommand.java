package com.example.command.inventory;

import com.example.command.CommandGroups;
import com.example.domain.ProductAvailability;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-20
 * Time: 8:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class RetrieveProductAvailabilityCommand extends HystrixCommand<ProductAvailability> {
	private final String productId;
	public RetrieveProductAvailabilityCommand(String productId) {
		super(HystrixCommandGroupKey.Factory.asKey(CommandGroups.INVENTORY));
		this.productId = productId;
	}

	@Override
	protected ProductAvailability run() throws Exception {
		Client client = ClientBuilder.newClient();
		Invocation invocation = client.target("http://localhost:3000/api/availability/" + productId)
				.request()
				.buildGet();
		Response response = invocation.invoke();
		return  response.readEntity(ProductAvailability.class);
	}
}
