package com.example.server;

import com.github.kristofa.brave.*;
import com.github.kristofa.brave.http.DefaultSpanNameProvider;
import com.github.kristofa.brave.http.SpanNameProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import java.util.Random;

/**
 * Created by hyleung on 2016-03-21.
 */
public class BraveTraceModule extends AbstractModule {
    @Provides
    public SpanCollector spanCollector() {
        return new LoggingSpanCollector();
    }

    @Provides
    public ServerTracer getServerTracer(final SpanCollector spanCollector) {
        return ServerTracer.builder()
                .state(new ThreadLocalServerClientAndLocalSpanState(0,0,"foo"))
                .traceSampler(Sampler.create(1.0f))
                .randomGenerator(new Random())
                .spanCollector(spanCollector).build();
    }

    @Provides
    public Brave brave(final SpanCollector spanCollector) {
        Brave.Builder builder = new Brave.Builder("brave-jersey2");
        return builder.spanCollector(spanCollector).build();
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
