package com.infobip.services.urlshortener.tests.suites;

import com.infobip.services.urlshortener.tests.AbstractTest;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Created by andrew on 21/08/16.
 */
public class AccountResourceAcceptanceTest extends AbstractTest {
    @Test
    public void createSuccessTest() {
        Map<String, Object> requestBean = new HashMap<>();
        requestBean.put("accountId", "a1");

        Map<String, Object> responseBean = performPost("/account", requestBean);

        assertTrue((boolean) responseBean.get("success"));
    }

    @Test
    public void createSuccessPasswordTest() {
        Map<String, Object> requestBean = new HashMap<>();
        requestBean.put("accountId", "a1");

        Map<String, Object> responseBean = performPost("/account", requestBean);

        assertNotNull(responseBean.get("password"));
        assertTrue(Pattern.matches("[a-zA-Z0-9]{8}", (CharSequence) responseBean.get("password")));
    }

    @Test
    public void createDuplicateTest() {
        Map<String, Object> requestBean = new HashMap<>();
        requestBean.put("accountId", "a1");

        Map<String, Object> responseBeanFirst = performPost("/account", requestBean);
        assertTrue((boolean) responseBeanFirst.get("success"));

        try {
            Map<String, Object> responseBeanSecond = performPost("/account", requestBean);
            fail();
        } catch (Exception ex) {
            assertEquals(ex.getMessage(), "HTTP 409 Conflict");
        }
    }

    @Test
    public void createValidationTest() {
        Map<String, Object> requestBean = new HashMap<>();

        try {
            Map<String, Object> responseBean = performPost("/account", requestBean);
            fail();
        } catch (Exception ex) {
            assertEquals(ex.getMessage(), "HTTP 412 Precondition Failed");
        }
    }
}
