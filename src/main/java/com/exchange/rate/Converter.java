package com.exchange.rate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.github.cdimascio.dotenv.Dotenv;

public class Converter {
    private String API_KEY;
    private String BASE_URL;
    private HttpClient client;
    private Gson gson;

    public Converter() {
        Dotenv dotenv = Dotenv.load();
        this.API_KEY = dotenv.get("API_KEY");
        this.client = HttpClient.newHttpClient();
        this.BASE_URL = "https://v6.exchangerate-api.com/v6/%s".formatted(this.API_KEY);
        this.gson = new Gson();
    }

    public Map<String, String> getSupportedCurrencies() throws IOException, InterruptedException {
        String path = "/codes";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.BASE_URL + path))
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        JsonObject result = gson.fromJson(response.body(), JsonObject.class);
        JsonArray conversionRatesArray = result.get("supported_codes").getAsJsonArray();

        Map<String, String> conversionRates = new HashMap<>();
        for (JsonElement entry : conversionRatesArray) {
            JsonArray array = entry.getAsJsonArray();
            conversionRates.put(array.get(0).getAsString(), array.get(1).getAsString());
        }

        return conversionRates;
    }

    public ConversionInfo getExchangeRate(String from, String to, double amount)
            throws IOException, InterruptedException {
        String path = "/pair/%s/%s/%f".formatted(from, to, amount);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.BASE_URL + path))
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        ConversionResult result = gson.fromJson(response.body(), ConversionResult.class);

        if (result.result().equals("success")) {
            return new ConversionInfo(result, amount);
        }

        throw new ConversionExeption("An error ocurred while trying to make the conversion.");
    }
}
