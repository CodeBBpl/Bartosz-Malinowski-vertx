package pl.malinowski.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
class Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @GetMapping(value = "simple", produces = "text/plain")
    public String simple() {
        LOGGER.info(Thread.currentThread().getName() + " In simple");
        return "Spring - simple! " + LocalDateTime.now();
    }


    @GetMapping(value = "complex")
    public String complex() throws InterruptedException {
        LOGGER.info(Thread.currentThread().getName() + " In complex");
        final String text = doComplexJob();
        return text;
    }

    private String doComplexJob() throws InterruptedException {
        Thread.sleep(5000);
        return "Spring - complex! " + LocalDateTime.now();
    }
}
