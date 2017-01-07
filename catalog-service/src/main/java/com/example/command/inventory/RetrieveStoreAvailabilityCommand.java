package com.example.command.inventory;

import com.example.command.CommandGroups;
import com.example.domain.StoreAvailability;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-20
 * Time: 9:19 PM
 * To change this template use File | Settings | File Templates.
 */
class RetrieveStoreAvailabilityCommand extends HystrixCommand<Optional<List<StoreAvailability>>> {
	private final String productId;
	private final String serviceHost;
	private final int servicePort;
	private final ClientRequestFilter clientRequestFilter;
	private final ClientResponseFilter clientResponseFilter;
	private static final Logger LOGGER = LoggerFactory.getLogger(RetrieveStoreAvailabilityCommand.class);
	public RetrieveStoreAvailabilityCommand(final String productId,
											final ClientRequestFilter requestFilter,
											final ClientResponseFilter responseFilter) {
		super(Setter
				.withGroupKey(HystrixCommandGroupKey.Factory.asKey(CommandGroups.INVENTORY))
				.andCommandKey(HystrixCommandKey.Factory.asKey("Store Availability")));
		this.productId = productId;
		this.clientRequestFilter = requestFilter;
		this.clientResponseFilter = responseFilter;
		this.serviceHost = System.getProperty("availability.service.host","http://localhost");
		this.servicePort = Integer.parseInt(System.getProperty("availability.service.port","9000"));
	}

	@Override
	protected Optional<List<StoreAvailability>> run() throws Exception {
		Client client = ClientBuilder.newClient();
		client.register(clientRequestFilter);
		client.register(clientResponseFilter);
		String uri = String.format("%s:%d/api/inventory/%s", serviceHost, servicePort, productId);
		LOGGER.info(uri);
		Invocation invocation = client
				.target(uri)
				.request()
				.buildGet();
		Response response = invocation.invoke();
		String json  = response.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();

		return Optional.of(mapper.readValue(json, new TypeReference<List<StoreAvailability>>() {
		}));
	}

	@Override
	protected Optional<List<StoreAvailability>> getFallback() {
		LOGGER.warn("Executing fallback");
		return Optional.empty();
	}
}
