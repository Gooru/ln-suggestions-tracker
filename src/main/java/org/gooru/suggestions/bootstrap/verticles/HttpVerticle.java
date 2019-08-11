package org.gooru.suggestions.bootstrap.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import org.gooru.suggestions.app.components.Finalizer;
import org.gooru.suggestions.app.components.Finalizers;
import org.gooru.suggestions.app.components.Initializer;
import org.gooru.suggestions.app.components.Initializers;
import org.gooru.suggestions.bootstrap.SuggestionsRunner;
import org.gooru.suggestions.routes.RouteConfiguration;
import org.gooru.suggestions.routes.RouteConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish on 3/11/17.
 */
public class HttpVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpVerticle.class);

  @Override
  public void start(Future<Void> startFuture) {
    LOGGER.info("Starting Http Verticle ...");
    final HttpServer httpServer = vertx.createHttpServer();

    final Router router = Router.router(vertx);
    configureRoutes(router);

    final int port = config().getInteger("http.port");
    LOGGER.info("Http Verticle starting on port: '{}'", port);
    httpServer.requestHandler(router::accept).listen(port, result -> {
      if (result.succeeded()) {
        LOGGER.info("Http Verticle started successfully");
        initializeApplication(startFuture);
      } else {
        LOGGER.error("Http Verticle failed to start", result.cause());
        startFuture.fail(result.cause());
      }
    });

  }

  @Override
  public void stop(Future<Void> stopFuture) {
    // Currently a no op
    finalizeApplication(stopFuture);
  }

  private void configureRoutes(final Router router) {
    RouteConfiguration rc = new RouteConfiguration();
    for (RouteConfigurator configurator : rc) {
      configurator.configureRoutes(vertx, router, config());
    }
  }

  private void initializeApplication(Future<Void> startFuture) {
    vertx.executeBlocking(future -> {
      Initializers initializers = new Initializers();
      for (Initializer initializer : initializers) {
        initializer.initializeComponent(vertx, SuggestionsRunner.getGlobalConfiguration());
      }
      future.complete();
    }, ar -> {
      if (ar.succeeded()) {
        LOGGER.info("Application initialization done");
        startFuture.complete();
      } else {
        LOGGER.warn("Application initialization failed", ar.cause());
        startFuture.fail(ar.cause());
      }
    });
  }

  private void finalizeApplication(Future<Void> stopFuture) {
    vertx.executeBlocking(future -> {
      Finalizers finalizers = new Finalizers();
      for (Finalizer finalizer : finalizers) {
        finalizer.finalizeComponent();
      }
      future.complete();
    }, ar -> {
      if (ar.succeeded()) {
        LOGGER.info("Application finalization done");
        stopFuture.complete();
      } else {
        LOGGER.warn("Application finalization failed", ar.cause());
        stopFuture.fail(ar.cause());
      }
    });
  }

}
