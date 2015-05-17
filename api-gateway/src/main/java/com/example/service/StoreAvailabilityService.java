package com.example.service;

import com.example.domain.StoreAvailability;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-16
 * Time: 7:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class StoreAvailabilityService {
	public List<StoreAvailability> retrieveStoreAvailability(String productId) throws IOException {
		Client client = ClientBuilder.newClient();
		Invocation invocation = client.target("http://localhost:9000/api/inventory/" + productId)
				.request()
				.buildGet();
		Response response = invocation.invoke();
		String json  = response.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();

		return mapper.readValue(json, new TypeReference<List<StoreAvailability>>() {});
	}
}
