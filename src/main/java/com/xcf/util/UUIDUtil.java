package com.xcf.util;

import java.util.UUID;

public class UUIDUtil {

    public static String generate() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}
