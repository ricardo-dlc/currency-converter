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
import java.util.Scanner;

public class Main {
    static String API_KEY;

    public static float getExchangeRate(String from, String to, float amount) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://v6.exchangerate-api.com/v6/%s/pair/%s/%s/%f".formatted(API_KEY, from, to, amount)))
                .build();

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

    public static float getAmountFromUser(String message, Scanner scanner) {
        float amount = 0;
        while (true) {
            System.out.print(message);

            if (!scanner.hasNextLine()) {
                break;
            }

            String userInput = scanner.nextLine();

            if (userInput.isEmpty()) {
                break;
            }

            try {
                amount = Float.parseFloat(userInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue; // Continue to the next iteration of the loop
            }

        }
        return amount;
    }

    public static int getCurrencyCodeFromUser(String message, Scanner scanner) {
        int userChoice = 0;
        while (true) {
            System.out.print(message);

            if (!scanner.hasNextLine()) {
                break;
            }

            String userInput = scanner.nextLine();

            if (userInput.isEmpty()) {
                break;
            }

            int choice;
            try {
                choice = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue; // Continue to the next iteration of the loop
            }

            // Check if the choice is within the valid range
            if (choice < 1 || choice > 7) {
                System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                continue; // Continue to the next iteration of the loop
            }

            userChoice = choice;
            break;
        }
        return userChoice;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int from;
        int to;
        float amount;
        Scanner scanner = new Scanner(System.in);
        String[] currencyCodes = new String[] { "MXN", "ARS", "BOB", "BRL", "CLP", "COP", "USD" };

        Dotenv dotenv = Dotenv.load();
        API_KEY = dotenv.get("API_KEY");

        String currencies = """
                (1) MXN - Mexican Peso
                (2) ARS - Argentine Peso
                (3) BOB - Bolivian Boliviano
                (4) BRL - Brazilian Real
                (5) CLP - Chilean Peso
                (6) COP - Colombian Peso
                (7) USD - United States Dollar
                """;
        String startMenu = """
                Welcome to the Currency converter app.
                Please choose from the following currencies to convert:
                %s
                Press Enter to Exit
                """.formatted(currencies);
        String continueMenu = """
                Now choose a target currency to convert to:
                %s
                Press Enter to Exit
                """.formatted(currencies);

        while (true) {
            from = getCurrencyCodeFromUser(startMenu, scanner);
            if (from == 0) {
                System.out.println("Exiting...");
                break;
            }
            to = getCurrencyCodeFromUser(continueMenu, scanner);
            if (to == 0) {
                System.out.println("Exiting...");
                break;
            }
            String selectedCurrencies = """
                    You chose to convert from %s to %s
                    Enter the amount:
                    """.formatted(currencyCodes[from - 1],
                    currencyCodes[to - 1]);
            amount = getAmountFromUser(selectedCurrencies, scanner);
            double conversionResult = getExchangeRate(currencyCodes[from - 1], currencyCodes[to - 1], amount);
            System.out.println(
                    "%.4f %s is equal to %.4f %s".formatted(amount, currencyCodes[from - 1],
                            conversionResult, currencyCodes[to - 1]));
        }
        scanner.close();
    }
}
