package com.adiha.wealthrating.utils;

import lombok.experimental.UtilityClass;

/**
 * Utility class that holds constants used in the wealth rating application.
 */
@UtilityClass
public class Constants {

    /**
     * API endpoint for evaluating asset information based on the city.
     */
    public static final String ASSET_EVALUATION_API = "central-bank/regional-info/evaluate?city={city}";

    /**
     * API endpoint for retrieving the wealth threshold.
     */
    public static final String WEALTH_THRESHOLD_API = "central-bank/wealth-threshold";
}
