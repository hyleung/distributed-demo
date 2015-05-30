package com.netflix.hystrix.dashboard;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-29
 * Time: 10:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class App {
	private static Logger LOGGER = LoggerFactory.getLogger(App.class);
	public static void main(String[] args)  {
		final String contextPath = System.getProperty("jettyRun.contextPath","/");
		final int port = Integer.parseInt(System.getProperty("jettyRun.httpPort", "7979"));
		LOGGER.info("Starting Hystrix dashboard on port {}, context path => {}", port, contextPath);
		final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath(contextPath);

		final Server server = new Server(port);
		server.setHandler(context);

		ServletHolder jerseyServlet = context.addServlet(com.netflix.hystrix.dashboard.stream.MockStreamServlet.class, "/mock.stream");
		jerseyServlet.setInitOrder(0);

		ServletHolder hystrixStreamServlet = context.addServlet(com.netflix.hystrix.dashboard.stream.ProxyStreamServlet.class, "/proxy.stream");
		hystrixStreamServlet.setInitOrder(1);

		Resource webapp = Resource.newResource(App.class.getClassLoader().getResource("webapp"));
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setBaseResource(webapp);

		HandlerList handlerList = new HandlerList();
		handlerList.setHandlers(new Handler[]{
				resourceHandler,
				context
		});

		server.setHandler(handlerList);

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		} finally {
			server.destroy();
		}
	}
}
