package com.exchange.rate;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class ConversionInfo {
    private ConversionResult conversionResult;
    private double amount;

    public ConversionInfo(ConversionResult conversionResult, double amount) {
        this.conversionResult = conversionResult;
        this.amount = amount;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault());
        Instant instant = Instant.ofEpochSecond(this.conversionResult.time_last_update_unix());
        String dateFormatted = formatter.format(instant);
        return "At an exchange rate of %.4f, %.2f %s is equivalent to %.2f %s (updated %s).".formatted(
                this.conversionResult.conversion_rate(),
                this.amount,
                this.conversionResult.base_code(),
                this.conversionResult.conversion_result(),
                this.conversionResult.target_code(),
                dateFormatted);
    }
}
