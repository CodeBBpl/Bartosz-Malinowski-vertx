import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class SimpleHttpServer extends AbstractVerticle {

    @Override
    public void start() {
        vertx.createHttpServer().requestHandler(req ->
            req.response().end("Hello from Vert.x!"))
        .listen(8080);
    }
	
}
