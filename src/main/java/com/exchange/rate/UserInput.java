package com.exchange.rate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserInput {
    private Scanner scanner;

    public UserInput() {
        this.scanner = new Scanner(System.in);
    }

    public float getAmountFromUser(String helper) {
        float amount = 0;
        while (true) {
            System.out.print(helper);

            String userInput = this.scanner.nextLine();

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

    public String getCurrencyCodeFromUser(String message, Map<String, String> supportedCurrencies) {
        String userInput = "";
        while (!(supportedCurrencies.get(userInput.toUpperCase()) != null)) {
            System.out.print(message);

            userInput = this.scanner.nextLine();

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

    public void closeScanner() {
        scanner.close();
    }
}
