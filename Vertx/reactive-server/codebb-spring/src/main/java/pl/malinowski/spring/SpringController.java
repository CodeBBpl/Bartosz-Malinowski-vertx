package pl.malinowski.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
class SpringController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringController.class);

    @GetMapping(value = "/", produces = "text/plain")
    public String simple() {
        LOGGER.info(Thread.currentThread().getName() + " In simple");
        return "Spring - simple! " + LocalDateTime.now();
    }


    @GetMapping(value = "complex")
    public String complex() throws InterruptedException {
        LOGGER.info(Thread.currentThread().getName() + " In complex");
        doComplexJob();
        return "Spring - complex! " + LocalDateTime.now();
    }

    private void doComplexJob() throws InterruptedException {
        Thread.sleep(2000);
    }
}
