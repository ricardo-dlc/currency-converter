package com.exchange.rate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class Main {
    static String API_KEY;

    public static float getExchangeRate(String from, String to, double amount) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://v6.exchangerate-api.com/v6/%s/pair/%s/%s/%f".formatted(API_KEY, from, to, amount)))
                .build();
        System.out.println(request.uri());
        float conversionResult = 0;
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            String responseBody = response.body();

            Gson gson = new Gson();
            JsonObject exchangeRateResponse = gson.fromJson(responseBody, JsonObject.class);
            String resultStatus = exchangeRateResponse.get("result").getAsString();

            if (resultStatus.equals("success")) {
                conversionResult = exchangeRateResponse.get("conversion_result").getAsFloat();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return conversionResult;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Dotenv dotenv = Dotenv.load();
        API_KEY = dotenv.get("API_KEY");
        System.out.println(getExchangeRate("USD", "MXN", 2.5));
    }
}
