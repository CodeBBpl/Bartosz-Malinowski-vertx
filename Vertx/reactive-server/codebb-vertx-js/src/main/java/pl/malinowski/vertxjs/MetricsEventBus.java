package pl.malinowski.vertxjs;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;
import io.vertx.ext.dropwizard.MetricsService;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

import java.util.Random;

public class MetricsEventBus extends AbstractVerticle {

    @Override
    public void start() {
        MetricsService service = MetricsService.create(vertx);
        Router router = Router.router(vertx);
        BridgeOptions options = new BridgeOptions().
            addOutboundPermitted(
                new PermittedOptions().
                    setAddress("metrics")
            );

        router.route("/eventbus/*").handler(SockJSHandler.create(vertx).bridge(options));

        // Serve the static resources
        router.route().handler(StaticHandler.create());

        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(router::accept).listen(8080);

        // Send a metrics events every second
        vertx.setPeriodic(1000, t -> {
            JsonObject metrics = service.getMetricsSnapshot(vertx.eventBus());
            vertx.eventBus().publish("metrics", metrics);
        });

        // Send some messages
        Random random = new Random();
        vertx.eventBus().consumer("whatever", msg -> {
            vertx.setTimer(10 + random.nextInt(50), id -> {
                vertx.eventBus().send("whatever", "hello");
            });
        });
        vertx.eventBus().send("whatever", "hello");
    }

    public static void main(String[] args) {
        Vertx
            .vertx(new VertxOptions().
                setMetricsOptions(new DropwizardMetricsOptions().setEnabled(true)))
            .deployVerticle(new MetricsEventBus());
    }

}
