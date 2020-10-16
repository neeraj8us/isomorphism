package com.neer.util;

import java.security.MessageDigest;

public class Util {

    static MessageDigest md;
    static {
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
    public static String getHashHex(String input) {
        byte[] digest = md.digest(input.getBytes());
        StringBuilder hexString = new StringBuilder();

        for (byte b : digest) {
            hexString.append(Integer.toHexString(0xFF & b));
        }
        return hexString.toString();
    }

    public static void printArray(String[] arr) {
        for (String s: arr) {
            System.out.println(s);
        }
        System.out.println();
    }


}
