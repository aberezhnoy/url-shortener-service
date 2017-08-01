package com.infobip.services.urlshortener.tests.suites;

import com.infobip.services.urlshortener.tests.AbstractTest;
import org.glassfish.jersey.client.ClientProperties;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by andrew on 21/08/16.
 */
public class UrlShortenerResourceAcceptanceTest extends AbstractTest {
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
    public void unknowUrlRedirectionTest() {
        int status = target("/xY0gfg").request().get().getStatus();
        assertEquals(status, 404);
    }

    @Test
    public void createUrlShortenerTest() {
        Map<String, Object> requestBean = new HashMap<>();
        requestBean.put("url", "http://google.com/");

        Map<String, Object> responseBean = performPost("/register", requestBean);

        assertNotNull(responseBean.get("shortUrl"));
        assertEquals(responseBean.get("shortUrl"), "http://localhost:8080/b8T84I");
    }

    @Test
    public void successUrlRedirectionTest() {
        String targetUrl = "http://google.com/";
        Map<String, Object> requestBean = new HashMap<>();
        requestBean.put("url", targetUrl);

        Map<String, Object> responseBean = performPost("/register", requestBean);

        assertEquals(responseBean.get("shortUrl"), "http://localhost:8080/b8T84I");

        Response response = target("/b8T84I")
            .property(ClientProperties.FOLLOW_REDIRECTS, false)
            .request()
            .get();

        assertEquals(response.getStatus(), 302);
        assertEquals(response.getHeaderString("Location"), targetUrl);
    }

    @Test
    public void createValidationTest() {
        Map<String, Object> requestBean = new HashMap<>();

        try {
            Map<String, Object> responseBean = performPost("/register", requestBean);
            fail();
        } catch (Exception ex) {
            assertEquals(ex.getMessage(), "HTTP 412 Precondition Failed");
        }
    }
}
