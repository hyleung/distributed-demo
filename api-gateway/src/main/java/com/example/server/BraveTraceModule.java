package com.example.server;

import com.github.kristofa.brave.*;
import com.github.kristofa.brave.http.DefaultSpanNameProvider;
import com.github.kristofa.brave.http.ServiceNameProvider;
import com.github.kristofa.brave.http.SpanNameProvider;
import com.github.kristofa.brave.http.StringServiceNameProvider;
import com.github.kristofa.brave.jaxrs2.BraveClientRequestFilter;
import com.github.kristofa.brave.jaxrs2.BraveClientResponseFilter;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseFilter;

/**
 * Created by hyleung on 2016-03-21.
 */
public class BraveTraceModule extends AbstractModule {

    @Provides
    public Brave brave() {
        return new Brave.Builder("brave-jersey-2").build();
    }

    @Provides
    public ServerTracer getServerTracer(final Brave brave) {
        return brave.serverTracer();
    }

    @Provides
    public LocalTracer localTracer(final Brave brave) {
        return brave.localTracer();
    }

    @Provides
    public ClientRequestInterceptor clientRequestInterceptor(final Brave brave) {
        return brave.clientRequestInterceptor();
    }

    @Provides
    public ClientResponseInterceptor clientResponseInterceptor(final Brave brave) {
        return brave.clientResponseInterceptor();
    }

    @Provides
    public ServerResponseInterceptor serverResponseInterceptor(final ServerTracer tracer) {
        return new ServerResponseInterceptor(tracer);
    }

    @Provides
    public ServerRequestInterceptor serverRequestInterceptor(final ServerTracer tracer) {
        return new ServerRequestInterceptor(tracer);
    }

    @Provides
    public SpanNameProvider spanNameProvider() {
        return new DefaultSpanNameProvider();
    }

    @Provides
    public ServiceNameProvider serviceNameProvider() {
        return new StringServiceNameProvider("api-gateway");
    }

    @Override
    protected void configure() {
        bind(ClientRequestFilter.class).to(BraveClientRequestFilter.class).asEagerSingleton();
        bind(ClientResponseFilter.class).to(BraveClientResponseFilter.class).asEagerSingleton();
    }
}
