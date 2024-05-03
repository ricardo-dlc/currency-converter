package com.exchange.rate;

import java.io.IOException;
import java.util.List;
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

    public static String getCurrencyCodeFromUser(String message, List<String> supportedCurrencies, Scanner scanner) {
        String userInput = "";
        while (true) {
            System.out.print(message);

            userInput = scanner.nextLine();

            if (userInput.isEmpty()) {
                break;
            }

            if (supportedCurrencies.contains(userInput.toUpperCase())) {
                break;
            } else {
                System.out.println("Invalid input. Please provide a valid code.");
            }
        }
        return userInput;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String from;
        String to;
        double amount = 0;
        Converter converter = new Converter();
        List<String> supportedCurrencies = converter.getSupportedCurrencies();
        Scanner scanner = new Scanner(System.in);
        String helper = "Type in a currency code (Press Enter to Exit): ";

        String startMenu = """
                Welcome to the Currency Converter App.
                Please choose from the following currencies to convert:
                %s""".formatted(String.join(", ", supportedCurrencies));

        String continueMenu = """
                Now choose a target currency to convert to:
                %s""".formatted(String.join(", ", supportedCurrencies));

        String selectedCurrencies = """
                You chose to convert from %s to %s""";

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
            double conversionResult = converter.getExchangeRate(from, to, amount);
            System.out.println(
                    "%.4f %s is equal to %.4f %s".formatted(amount, from, conversionResult, to));
        }
        scanner.close();
    }
}
