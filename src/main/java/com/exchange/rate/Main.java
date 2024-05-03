package com.exchange.rate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static float getAmountFromUser(String helper, Scanner scanner) {
        float amount = 0;
        while (true) {
            System.out.print(helper);

            String userInput = scanner.nextLine();

            try {
                amount = Float.parseFloat(userInput);
                if (amount > 0) {
                    break;
                }
                System.out.println("Please provide a value greater than 0.");
                continue;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

        }
        return amount;
    }

    public static String getCurrencyCodeFromUser(String message, Map<String, String> supportedCurrencies,
            Scanner scanner) {
        String userInput = "";
        while (!(supportedCurrencies.get(userInput.toUpperCase()) != null)) {
            System.out.print(message);

            userInput = scanner.nextLine();

            if (userInput.isEmpty()) {
                break;
            }

            if (supportedCurrencies.get(userInput.toUpperCase()) == null) {
                List<Map.Entry<String, String>> sortedEntries = new ArrayList<>(supportedCurrencies.entrySet());
                Collections.sort(sortedEntries, Comparator.comparing(Map.Entry::getKey));
                System.out.println("Invalid input. Please provide a valid code.");
                System.out.println("Valid codes are: " + String.join(", ", sortedEntries + "."));
            }
        }

        return userInput.toUpperCase();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Converter converter = new Converter();
        String from;
        String to;
        double amount = 0;

        try {
            Map<String, String> supportedCurrencies = converter.getSupportedCurrencies();
            String helper = "Type in a currency code (Press Enter to Exit): ";

            String startMenu = "Welcome to the Currency Converter App.\nChoose a currency to convert.";

            String continueMenu = "Now choose a target currency to convert to.";

            String selectedCurrencies = "You chose to convert from %s to %s.";

            while (true) {
                System.out.println(startMenu);
                from = getCurrencyCodeFromUser(helper, supportedCurrencies, scanner);
                if (from.isEmpty()) {
                    System.out.println("Exiting...");
                    break;
                }

                System.out.println(continueMenu);
                to = getCurrencyCodeFromUser(helper, supportedCurrencies, scanner);
                if (to.isEmpty()) {
                    System.out.println("Exiting...");
                    break;
                }

                System.out.println(selectedCurrencies.formatted(from, to));
                amount = getAmountFromUser("Enter the amount: ", scanner);
                ConversionInfo conversionResult = converter.getExchangeRate(from, to, amount);
                System.out.println(conversionResult);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }
}
