package com.example.tracing;

import com.google.inject.Inject;
import ratpack.handling.Handler;
import ratpack.handling.HandlerDecorator;
import ratpack.handling.Handlers;
import ratpack.registry.Registry;

import javax.inject.Singleton;

/**
 * Created by hyleung on 2016-03-29.
 */
@Singleton
public class BraveTracingDecorator implements HandlerDecorator {
    private final BraveTracingHandler tracingHandler;

    @Inject
    public BraveTracingDecorator(BraveTracingHandler tracingHandler) {
        this.tracingHandler = tracingHandler;
    }

    @Override
    public Handler decorate(Registry serverRegistry, Handler rest) throws Exception {
        return Handlers.chain(tracingHandler, rest);
    }
}
