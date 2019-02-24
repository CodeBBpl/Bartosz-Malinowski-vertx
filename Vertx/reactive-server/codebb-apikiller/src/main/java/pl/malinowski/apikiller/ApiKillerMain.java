package pl.malinowski.apikiller;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ApiKillerMain {

    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newFixedThreadPool(200);

        try {
            for(int i = 0; i < 200; i++) {
                executorService.submit(ApiKillerMain::callIt);
            }
        } finally {
            executorService.shutdown();
        }

        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    private static void callIt() {
        try {
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpGet request = new HttpGet("http://localhost:8080/complex");
            final HttpResponse response = client.execute(request);
            final BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while((line = rd.readLine()) != null) {
                System.out.println(line);
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
