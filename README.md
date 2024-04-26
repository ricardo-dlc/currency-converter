# currency-converter

## Pre-requisites

This project uses the ExchangeRate-API, so you should have an API key prior to run this project. [Click here](https://app.exchangerate-api.com/sign-up) to get yours.

## Environment

After you get your API key, create an .env file at the project's root level with the following content:

```
API_KEY=YOUR-API-KEY
```

Replace `YOUR-API-KEY` with your actual API key.

## Functionality

You can select from 7 different currencies to convert to and from. The first choice is the base code (the currency you want to convert from). The second choice is the target code (the currency you want to convert to). After selecting the two currencies, you should input the amount you want to convert, the program will output the result of the conversion.

For example convertin from USD to MXN:

![image](https://github.com/ricardo-dlc/currency-converter/assets/7584551/aee7b206-3847-4076-a5e0-330717e38ee1)

To exit the program, press <kbd>Enter</kbd> key during the currency selection.
