package com.infobip.services.urlshortener.utils;

/**
 * Created by andrew
 */
public final class UrlShortenerUtils {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = ALPHABET.length();

    // TODO: cut 4 bit to 16 bits
    public static String base62Encode(long i) {
        if (i == 0) {
            return "a";
        }

        StringBuilder stringBuilder = new StringBuilder();

        while (i > 0) {
            long pos = i % BASE;

            stringBuilder.append(ALPHABET.charAt((int) pos));
            i = i / BASE;
        }

        return stringBuilder.reverse().toString();
    }

    public static int base62Decode(String str) {
        int num = 0;

        for ( int i = 0; i < str.length(); i++ ) {
            num = num * BASE + ALPHABET.indexOf(str.charAt(i));
        }

        return num;
    }

    public static String shortUrl(String url) {
        return base62Encode((long) url.hashCode() + (long) Integer.MAX_VALUE);
    }
}
