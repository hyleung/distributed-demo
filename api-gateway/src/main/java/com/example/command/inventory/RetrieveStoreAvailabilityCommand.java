package com.example.command.inventory;

import com.example.command.CommandGroups;
import com.example.domain.StoreAvailability;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-20
 * Time: 9:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class RetrieveStoreAvailabilityCommand extends HystrixCommand<Optional<List<StoreAvailability>>> {
	private final String productId;
	public RetrieveStoreAvailabilityCommand(String productId) {
		super(HystrixCommandGroupKey.Factory.asKey(CommandGroups.INVENTORY));
		this.productId = productId;
	}

	@Override
	protected Optional<List<StoreAvailability>> run() throws Exception {
		Client client = ClientBuilder.newClient();
		Invocation invocation = client.target("http://localhost:9000/api/inventory/" + productId)
				.request()
				.buildGet();
		Response response = invocation.invoke();
		String json  = response.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();

		return Optional.of(mapper.readValue(json, new TypeReference<List<StoreAvailability>>() {}));
	}

	@Override
	protected Optional<List<StoreAvailability>> getFallback() {
		return Optional.empty();
	}
}
