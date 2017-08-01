package com.infobip.services.urlshortener.tests.suites;

import com.infobip.services.urlshortener.tests.AbstractTest;
import org.glassfish.jersey.client.ClientProperties;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by andrew on 21/08/16.
 */
public class StatisticsResourceAcceptanceTest extends AbstractTest {
    @Before
    public void before() {
        String accountId = "a1";
        Map<String, Object> requestBean = new HashMap<>();
        requestBean.put("accountId", accountId);

        Map<String, Object> responseBean = performPost("/account", requestBean);
        String password = (String) responseBean.get("password");

        setAuthentication(accountId, password);
    }

    @Test
    public void emptyAccountUrlsTest() {
        Map<String, Object> responseBean = performGet("/statistic/a1");
        assertEquals(responseBean.size(), 0);
    }

    @Test
    public void notEmptyAccountUrlsTest() {
        Map<String, Object> requestBean = new HashMap<>();
        requestBean.put("url", "http://google.com/");
        performPost("/register", requestBean);

        Map<String, Object> responseBean = performGet("/statistic/a1");
        assertEquals(responseBean.size(), 1);
        assertEquals(responseBean.get("http://google.com/"), 0);
    }

    @Test
    public void notZeroStatsTest() {
        Map<String, Object> responseBean;
        Map<String, Object> requestBean = new HashMap<>();
        requestBean.put("url", "http://google.com/");
        performPost("/register", requestBean);

        responseBean = performGet("/statistic/a1");
        assertEquals(responseBean.size(), 1);
        assertEquals(responseBean.get("http://google.com/"), 0);

        target("/b8T84I")
            .property(ClientProperties.FOLLOW_REDIRECTS, false)
            .request()
            .get();

        target("/b8T84I")
            .property(ClientProperties.FOLLOW_REDIRECTS, false)
            .request()
            .get();

        responseBean = performGet("/statistic/a1");
        assertEquals(responseBean.size(), 1);
        assertEquals(responseBean.get("http://google.com/"), 2);
    }

    @Test
    public void securityTest() {
        try {
            performGet("/statistic/a2");
            fail();
        } catch (Exception ex) {
            assertEquals(ex.getMessage(), "HTTP 403 Forbidden");
        }

        setAuthentication(null, null);

        try {
            performGet("/statistic/a1");
            fail();
        } catch (Exception ex) {
            assertEquals(ex.getMessage(), "HTTP 401 Unauthorized");
        }
    }
}
