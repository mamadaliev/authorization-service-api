package com.github.egnaf.auth.utils;

import java.sql.Timestamp;

public class TimestampHelper {

    public static long getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis()).getTime();
    }
}
