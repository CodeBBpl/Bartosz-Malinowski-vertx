package pl.malinowski.eventbus;

import io.vertx.core.AbstractVerticle;

import static pl.malinowski.eventbus.SimpleEventBus.ADDRESS;


public class SimpleConsumer extends AbstractVerticle {

    @Override
    public void start() {
        vertx.eventBus().consumer(ADDRESS, message -> {
            System.out.println(Thread.currentThread().getName() + " " + message.body());
            message.reply("Thanks for " + message.body());
        });
    }
}
