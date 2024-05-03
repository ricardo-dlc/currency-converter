package com.exchange.rate;

public record ConversionResult(
        String result,
        int time_last_update_unix,
        String base_code,
        String target_code,
        double conversion_rate,
        double conversion_result) {
}
