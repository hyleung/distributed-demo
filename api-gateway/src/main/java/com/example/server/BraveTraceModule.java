package com.example.server;

import com.github.kristofa.brave.*;
import com.github.kristofa.brave.http.DefaultSpanNameProvider;
import com.github.kristofa.brave.http.SpanNameProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

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

    @Override
    protected void configure() {

    }
}
