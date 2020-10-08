package com.demo;

import io.rollout.okhttp3.OkHttpClient;
import io.rollout.okhttp3.Request;
import io.rollout.okhttp3.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class KloiademoApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(KloiademoApplication.class, args);


        fetchAllExp();
    }

    private static void fetchAllExp() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://x-api.rollout.io/public-api/applications/5f7a4a8e9f76778f3ada616b/Production/experiments")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("authorization", "Bearer 4b74810d-b581-4474-b66c-ce0615e61a94")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body());
    }

}
