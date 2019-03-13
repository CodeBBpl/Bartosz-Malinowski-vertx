import io.vertx.core.AbstractVerticle;

public class Consumer extends AbstractVerticle {

	static final String ADDRESS = "ping-address";

    @Override
    public void start() {
        vertx.eventBus().consumer(ADDRESS, message -> 
            System.out.println("In consumer   " + Thread.currentThread().getName() + " " + message.body())
        );
    }
}
