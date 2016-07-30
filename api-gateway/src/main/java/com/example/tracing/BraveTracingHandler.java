package com.example.tracing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.handling.HandlerDecorator;
import ratpack.http.Request;

/**
 * Created by hyleung on 2016-03-29.
 */
public class BraveTracingHandler implements Handler {
    private static final Logger LOGGER = LoggerFactory.getLogger(BraveTracingHandler.class);

    public static HandlerDecorator decorator() {
        return HandlerDecorator.prepend(new BraveTracingHandler());
    }

    @Override
    public void handle(Context ctx) throws Exception {
        Request request = ctx.getRequest();
        LOGGER.info("Request: {}", request);
        ctx.getResponse().beforeSend(response -> {
            LOGGER.info("Response: {}", response);
        });
        ctx.next();
    }


}
