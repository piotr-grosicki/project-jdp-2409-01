package com.kodilla.ecommercee.generator;

import java.util.Random;

public class KeyGenerator {
    private static final Random RANDOM = new Random();

    public static Integer generateKey(Long id) {
        int key = 0;

        if (id != null) {
            key = (int)((id % Integer.MAX_VALUE) * 1_000_000L);
        }

        return (key + RANDOM.nextInt(1_000_000)) % Integer.MAX_VALUE;
    }
}
