package com.infobip.services.urlshortener.tests.suites;

import com.infobip.services.urlshortener.utils.UrlShortenerUtils;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Created by andrew on 21/08/16.
 */
public class UrlShortenerUnitTest {
    @Test
    public void regexpTest() {
        String shortUrl = UrlShortenerUtils.shortUrl("http://google.com/");
        assertTrue(Pattern.matches("[a-zA-Z0-9]+", shortUrl));
    }

    @Test
    public void equivalentTest() {
        String shortUrl = UrlShortenerUtils.shortUrl("http://google.com/");

        for (int i = 0; i < 1000; i++) {
            assertEquals(shortUrl, UrlShortenerUtils.shortUrl("http://google.com/"));
        }
    }

    @Test
    public void inequivalentTest() {
        Set<String> urlSet = new HashSet<>();
        String shortUrl;

        shortUrl = UrlShortenerUtils.shortUrl("http://google.com/");
        assertFalse(urlSet.contains(shortUrl));
        urlSet.add(shortUrl);

        shortUrl = UrlShortenerUtils.shortUrl("http://google.com");
        assertFalse(urlSet.contains(shortUrl));
        urlSet.add(shortUrl);

        shortUrl = UrlShortenerUtils.shortUrl("ttp://google.com/");
        assertFalse(urlSet.contains(shortUrl));
        urlSet.add(shortUrl);
    }
}
