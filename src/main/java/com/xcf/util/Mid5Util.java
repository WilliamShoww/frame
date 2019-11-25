package com.xcf.util;


import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;

public class Mid5Util {

    private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();

    private static String toHex(byte[] bytes) {
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < 32; i = i + 2) {
            byte bt = bytes[i / 2];
            ret.append(HEX_DIGITS[(bt >>> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bt & 0x0f]);
        }
        return ret.toString();
    }

    /**
     * mid5 简单加密
     *
     * @param content 内容
     */
    public static String decode16(String content) {
        if (null == content || "".equals(content)) {
            return content;
        }
        String decode = decode(content);
        return decode.substring(8, 24);
    }

    /**
     * mid5 简单加密
     *
     * @param content 内容
     */
    public static String decode(String content) {
        if (null == content || "".equals(content)) {
            return content;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content.getBytes());
            byte[] bytes = md.digest();
            return toHex(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * mid5 盐值加密
     *
     * @param content 加密内容
     * @param slat    盐值
     */
    public static String decode16(String content, String slat) {
        if (slat == null || "".equals(slat)) {
            return decode16(content);
        }
        char[] slats = slat.toCharArray();
        char[] preSlats = new char[slats.length / 2];
        char[] subSlats = new char[slats.length / 2 + 1];
        StringBuilder builder = new StringBuilder();
        int k = 0;
        int l = 0;
        for (int i = 0; i < slats.length; i++) {
            if (i % 2 == 0) {
                subSlats[k++] = slats[i];
            } else {
                preSlats[l++] = slats[i];
            }
        }

        builder.append(preSlats).append(content).append(subSlats);
        String s = builder.toString();
        return decode16(s);
    }

    /**
     * mid5 盐值加密
     *
     * @param content 加密内容
     * @param slat    盐值
     */
    public static String decode(String content, String slat) {
        if (slat == null || "".equals(slat)) {
            return decode(content);
        }
        char[] slats = slat.toCharArray();
        char[] preSlats = new char[slats.length / 2];
        char[] subSlats = new char[slats.length / 2 + 1];
        StringBuilder builder = new StringBuilder();
        int k = 0;
        int l = 0;
        for (int i = 0; i < slats.length; i++) {
            if (i % 2 == 0) {
                subSlats[k++] = slats[i];
            } else {
                preSlats[l++] = slats[i];
            }
        }

        builder.append(preSlats).append(content).append(subSlats);
        String s = builder.toString();
        return decode(s);
    }

    public static void main(String[] args) {
        final String content = "8554546ffdsfsfge喊你挂机房😄东计划twerwererwrwe";
        String decode = decode(content);
        System.out.println("decode = " + decode);
        String decode16 = decode16(content);
        System.out.println("decode16 = " + decode16);
        String spring = DigestUtils.md5Hex(content.getBytes());
        System.out.println("apache = " + spring);
    }
}
