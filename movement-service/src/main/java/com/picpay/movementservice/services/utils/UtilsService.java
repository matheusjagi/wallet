package com.picpay.movementservice.services.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class UtilsService {

    public static final Integer POSSIBILITY_FAILURE = 2;
    public static final Integer AMOUNT_POSSIBILITIES = 10;

    public UtilsService() {
        throw new IllegalStateException("Utility class");
    }

    public static Boolean createRandomBoolean() {
        Random random = new Random(System.currentTimeMillis());
        return random.nextBoolean();
//        List<Boolean> random = new ArrayList<>(Arrays.asList(new Boolean[AMOUNT_POSSIBILITIES])).stream()
//                .limit(POSSIBILITY_FAILURE)
//                .map(obj -> Boolean.FALSE)
//                .collect(Collectors.toList());
//
//        Collections.shuffle(random);
//
//        return random.stream()
//                .findAny()
//                .orElse(Boolean.TRUE);
    }
}
