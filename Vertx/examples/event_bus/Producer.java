
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

import java.time.LocalTime;

public class Producer extends AbstractVerticle {

    static final String ADDRESS = "ping-address";

    @Override
    public void start() {
        vertx.setPeriodic(500L, nb -> {
			System.out.println("Producer " + Thread.currentThread().getName());
            vertx.eventBus().send(ADDRESS, "Ping " + getCurrentTime());
        });
    }

    private String getCurrentTime() {
        return LocalTime.now().toString();
    }
}
