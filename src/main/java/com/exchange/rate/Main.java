package com.exchange.rate;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Converter converter = new Converter();
        UserInput userInput = new UserInput();
        String from;
        String to;
        double amount;

        try {
            Map<String, String> supportedCurrencies = converter.getSupportedCurrencies();
            String helper = "Type in a currency code (Press Enter to Exit): ";

            String startMenu = "Welcome to the Currency Converter App.\nChoose a currency to convert.";

            String continueMenu = "Now choose a target currency to convert to.";

            String selectedCurrencies = "You chose to convert from %s to %s.";

            while (true) {
                System.out.println(startMenu);
                from = userInput.getCurrencyCodeFromUser(helper, supportedCurrencies);
                if (from.isEmpty()) {
                    break;
                }

                System.out.println(continueMenu);
                to = userInput.getCurrencyCodeFromUser(helper, supportedCurrencies);
                if (to.isEmpty()) {
                    break;
                }

                System.out.println(selectedCurrencies.formatted(from, to));
                amount = userInput.getAmountFromUser("Enter the amount: ");
                ConversionInfo conversionResult = converter.getExchangeRate(from, to, amount);
                System.out.println(conversionResult);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Exiting...");

            userInput.closeScanner();
        }
    }
}
