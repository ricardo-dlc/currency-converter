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

You can choose from almost 161 commonly circulating world currencies to convert to and from ([supported by ExchangeRate-API](https://www.exchangerate-api.com/docs/supported-currencies)). The first input is the base code (the currency you want to convert from). The second input is the target code (the currency you want to convert to). After providing the two currency codes, you will be requested to provide the amount you want to convert. After providing all the required data, the program will output the result of the conversion.

For example convertin from USD to MXN:

![image](https://github.com/ricardo-dlc/currency-converter/assets/7584551/cd06f9cb-768e-4a28-9f30-878efa5e5d1d)

To exit the program, press <kbd>Enter</kbd> key during the currency requesting.

If you provide a wrong currency code, the program will notify and show you the supported codes (in ascending order).

![image](https://github.com/ricardo-dlc/currency-converter/assets/7584551/2aacca74-4972-4513-a88e-f61db030f251)

