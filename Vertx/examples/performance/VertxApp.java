import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.time.LocalDateTime;

public class VertxApp extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(VertxApp.class);

    @Override
    public void start() {
        final HttpServer server = vertx.createHttpServer();
        final Router router = Router.router(vertx);

        router.get("/").handler(this::handleSimple);
        router.get("/complex").blockingHandler(this::handleComplex, false);

        server.requestHandler(router::accept).listen(8080);
    }

    private void handleSimple(final RoutingContext routingContext) {
        LOGGER.info(Thread.currentThread().getName() + " In simple");
        final HttpServerResponse response = routingContext.response();
        final String text = "Vert.x - simple! " + LocalDateTime.now();
        response.putHeader("content-type", "application/json").end(text);
    }

    private void handleComplex(final RoutingContext routingContext) {
        try {
            LOGGER.info(Thread.currentThread().getName() + " In complex");
            Thread.sleep(2000);
            routingContext.response().end("Complex ! " + LocalDateTime.now());
        } catch(final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
