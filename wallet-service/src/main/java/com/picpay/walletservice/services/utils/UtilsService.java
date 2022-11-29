package com.picpay.walletservice.services.utils;

public class UtilsService {

    public static final int MAX_LENGTH_NUMBER = 18;

    public UtilsService() {
        throw new IllegalStateException("Utility class");
    }

    public static String createRandomNumber(Long length) {
        if (length > MAX_LENGTH_NUMBER) {
            throw new IllegalStateException("To many digits");
        }

        long len = (long) Math.pow(10, length - 1) * 9;
        long number = (long) (Math.random() * len) + (long) Math.pow(10, length - 1);

        String value = String.format("%d", number);

        if (value.length() != length) {
            throw new IllegalStateException("The random number '" + value + "' is not '" + length + "' digits");
        }

        return value;
    }
}
