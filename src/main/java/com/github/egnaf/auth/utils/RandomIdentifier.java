package com.github.egnaf.auth.utils;

import java.util.UUID;

public class RandomIdentifier {

    public static String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String generate(String key) {
        if (key != null) {
            return UUID.nameUUIDFromBytes(key.getBytes()).toString().replaceAll("-", "");
        } else {
            return RandomIdentifier.generate();
        }
    }
}
