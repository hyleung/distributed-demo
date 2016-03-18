package com.example.server;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.ServletModule;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import com.squarespace.jersey2.guice.JerseyGuiceModule;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-29
 * Time: 6:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class App {
	private static Logger LOGGER = LoggerFactory.getLogger(App.class);
	public static void main(String[] args) {
        List<Module> modules = new ArrayList<>();

        modules.add(new JerseyGuiceModule("__HK2_Generated_0"));
        modules.add(new ServletModule());
        modules.add(new AbstractModule() {
            @Override
            protected void configure() {
                // ...
            }
        });

        Injector injector = Guice.createInjector(modules);
        JerseyGuiceUtils.install(injector);


		final String contextPath = System.getProperty("jettyRun.contextPath","/");
		final int port = Integer.parseInt(System.getProperty("jettyRun.httpPort", "8080"));
		LOGGER.info("Starting Jetty on port {}, context path => {}", port, contextPath);
		final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath(contextPath);
		final Server server = new Server(port);
		server.setHandler(context);

		ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/api/*");
		jerseyServlet.setInitOrder(0);

		jerseyServlet.setInitParameter(
				"jersey.config.server.provider.packages",
				"com.example"
		);

		ServletHolder hystrixStreamServlet = context.addServlet(HystrixMetricsStreamServlet.class, "/hystrix.stream/*");
		hystrixStreamServlet.setInitOrder(1);

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
