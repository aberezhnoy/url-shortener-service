package com.infobip.services.urlshortener.tests.suites;

import com.infobip.services.urlshortener.utils.StringUtils;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Created by andrew on 21/08/16.
 */
public class StringGeneratorUnitTest {
    @Test
    public void regexpAndLengthTest() {
        String generatedString = StringUtils.generate(StringUtils.getValid("[a-z0-9]"), 8);

        assertTrue(Pattern.matches("[a-z0-9]{8}", generatedString));
    }

    @Test
    public void randomTest() {
        Set<String> generatedSet = new HashSet<>();
        char[] chars = StringUtils.getValid("[a-zA-Z0-9]");

        for (int i = 0; i < 1000; i++) {
            String generatedString = StringUtils.generate(chars, 8);
            assertFalse(generatedSet.contains(generatedString));
            generatedSet.add(generatedString);
        }
    }
}
