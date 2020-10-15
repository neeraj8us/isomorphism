package com.neer.util;

import java.security.MessageDigest;

public class Util {
    public static String getHashHex(String input) {
        byte[] digest = md.digest(input.getBytes());
        StringBuilder hexString = new StringBuilder();

        for (byte b : digest) {
            hexString.append(Integer.toHexString(0xFF & b));
        }
        return hexString.toString();
    }

    static MessageDigest md;
    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (Exception ignored) {
        }
    }
}
