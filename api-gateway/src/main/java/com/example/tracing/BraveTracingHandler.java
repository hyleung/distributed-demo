package com.example.tracing;

import com.github.kristofa.brave.*;
import com.github.kristofa.brave.http.*;
import com.github.kristofa.brave.internal.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.MutableHeaders;
import ratpack.http.Request;
import ratpack.http.Response;

import javax.inject.Inject;
import java.net.URI;
import java.util.*;

/**
 * Created by hyleung on 2016-03-29.
 */
public class BraveTracingHandler implements Handler {
    private static final Logger LOGGER = LoggerFactory.getLogger(BraveTracingHandler.class);
    private final ClientRequestInterceptor requestInterceptor;
    private final ClientResponseInterceptor responseInterceptor;
    private final ServiceNameProvider serviceNameProvider;
    private final SpanNameProvider spanNameProvider;

    @Inject
    public BraveTracingHandler(final ClientRequestInterceptor requestInterceptor,
                               final ClientResponseInterceptor responseInterceptor,
                               final ServiceNameProvider serviceNameProvider,
                               final SpanNameProvider spanNameProvider) {
        this.requestInterceptor = requestInterceptor;
        this.responseInterceptor = responseInterceptor;
        this.serviceNameProvider = serviceNameProvider;
        this.spanNameProvider = spanNameProvider;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        Request request = ctx.getRequest();

        LOGGER.info("Request: {}", request);
        final RatpackHttpClientRequest ratpackHttpClientRequest = new RatpackHttpClientRequest(request);
        ClientRequestAdapter clientRequestAdapter = new RequestAdapter(ratpackHttpClientRequest,
                serviceNameProvider,
                spanNameProvider);
        requestInterceptor.handle(clientRequestAdapter);
        ctx.getResponse().beforeSend(response -> {
            LOGGER.info("Response: {}", response);
            ratpackHttpClientRequest
                    .getHeaders()
                    .entrySet()
                    .stream()
                    .forEach(entry -> response.getHeaders().add(entry.getKey(), entry.getValue()));
            ClientResponseAdapter responseAdapter = new ResponseAdapter(new RatpackHttpClientResponse(response));
            responseInterceptor.handle(responseAdapter);
        });
        ctx.next();
    }

    private static class RatpackHttpClientRequest implements HttpClientRequest {
        private final Request request;
        private final Map<String, String> headers = new HashMap<>();
        public RatpackHttpClientRequest(final Request request) {
            this.request = request;
        }

        @Override
        public void addHeader(String header, String value) {
            headers.put(header, value);
        }

        @Override
        public URI getUri() {
            return  URI.create(request.getUri());
        }

        @Override
        public String getHttpMethod() {
            return request.getMethod().getName();
        }

        public Map<String, String> getHeaders() {
            return headers;
        }
    }

    private static class RatpackHttpClientResponse implements HttpResponse {
        private final Response response;

        public RatpackHttpClientResponse(final Response response) {
            this.response = response;
        }

        @Override
        public int getHttpStatusCode() {
            return response.getStatus().getCode();
        }
    }
    private static class RequestAdapter implements ClientRequestAdapter {
        private final ServiceNameProvider serviceNameProvider;
        private final SpanNameProvider spanNameProvider;
        private final HttpClientRequest request;
        RequestAdapter(final HttpClientRequest request,
                              final ServiceNameProvider serviceNameProvider,
                              final SpanNameProvider spanNameProvider) {
            this.request = request;
            this.serviceNameProvider = serviceNameProvider;
            this.spanNameProvider = spanNameProvider;
        }

        @Override
        public String getSpanName() {
            return spanNameProvider.spanName(request);
        }

        @Override
        public void addSpanIdToRequest(@Nullable SpanId spanId) {
            if (spanId == null) {
                request.addHeader(BraveHttpHeaders.Sampled.getName(), "0");
            } else {
                request.addHeader(BraveHttpHeaders.Sampled.getName(), "1");
                request.addHeader(BraveHttpHeaders.TraceId.getName(), IdConversion.convertToString(spanId.getTraceId()));
                request.addHeader(BraveHttpHeaders.SpanId.getName(), IdConversion.convertToString(spanId.getSpanId()));
                if (spanId.getParentSpanId() != null) {
                    request.addHeader(BraveHttpHeaders.ParentSpanId.getName(), IdConversion.convertToString(spanId.getParentSpanId()));
                }
            }
        }

        @Override
        public String getClientServiceName() {
            return serviceNameProvider.serviceName(request);
        }

        @Override
        public Collection<KeyValueAnnotation> requestAnnotations() {
            URI uri = request.getUri();
            KeyValueAnnotation annotation = KeyValueAnnotation.create("http.uri", uri.toString());
            return Arrays.asList(annotation);
        }
    }

    private static class ResponseAdapter  implements ClientResponseAdapter {
        private final HttpResponse response;

        public ResponseAdapter(final HttpResponse response) {
            this.response = response;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Collection<KeyValueAnnotation> responseAnnotations() {
            int httpStatus = response.getHttpStatusCode();

            if ((httpStatus < 200) || (httpStatus > 299)) {
                KeyValueAnnotation statusAnnotation = KeyValueAnnotation.create("http.responsecode", String.valueOf(httpStatus));
                return Arrays.asList(statusAnnotation);
            }
            return Collections.EMPTY_LIST;
        }
    }
}
