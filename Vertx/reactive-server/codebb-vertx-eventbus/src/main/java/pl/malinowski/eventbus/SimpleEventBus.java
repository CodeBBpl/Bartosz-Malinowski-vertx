package pl.malinowski.eventbus;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

import java.time.LocalTime;

public class SimpleEventBus extends AbstractVerticle {

    static final String ADDRESS = "ping-address";

    @Override
    public void start() {
        vertx.deployVerticle(new SimpleConsumer(), new DeploymentOptions().setWorker(true));
        vertx.setPeriodic(500L, nb ->
            vertx.eventBus()
                .send(ADDRESS, "Ping " + getCurrentTime(), resp ->
                    System.out.println(Thread.currentThread().getName() + " " + resp.result().body())
                )
        );
    }

    private String getCurrentTime() {
        return LocalTime.now().toString();
    }


    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new SimpleEventBus());
    }
}
