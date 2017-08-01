package com.infobip.services.urlshortener.utils;

import java.util.Arrays;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by andrew
 */
public final class StringUtils {
    public static String generate(char[] validChars, int len) {
        char[] password = new char[len];
        Random rand = new Random(System.nanoTime());

        for (int i = 0; i < len; i++) {
            password[i] = validChars[rand.nextInt(validChars.length)];
        }

        return new String(password);
    }

    public static char[] getValid(final String regex) {
        char[] potential = new char[Character.MAX_VALUE];
        int size = 0;
        final Pattern pattern = Pattern.compile(regex);

        for (int c = 0; c <= Character.MAX_VALUE; c++) {
            if (pattern.matcher(String.valueOf((char)c)).matches()) {
                potential[size++] = (char) c;
            }
        }
        return Arrays.copyOf(potential, size);
    }
}
