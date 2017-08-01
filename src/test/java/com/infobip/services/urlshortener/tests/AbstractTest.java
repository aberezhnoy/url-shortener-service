package com.infobip.services.urlshortener.tests;

import com.infobip.services.urlshortener.Bootstrap;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.test.JerseyTest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.xml.bind.DatatypeConverter;
import java.util.Map;

/**
 * Created by andrew on 21/08/16.
 */
public class AbstractTest extends JerseyTest {
    private String accountId;
    private String password;

    @Override
    protected Application configure() {
        return Bootstrap.createResourceConfig();
    }

    protected Map<String, Object> performPost(String url, Map<String, Object> requestBean) {
        return target(url)
            .property(ClientProperties.FOLLOW_REDIRECTS, false)
            .request()
            .header("Authorization", getAuthenticationHeader())
            .post(Entity.json(requestBean), Map.class);
    }

    protected Map<String, Object> performGet(String url) {
        return target(url)
            .property(ClientProperties.FOLLOW_REDIRECTS, false)
            .request()
            .header("Authorization", getAuthenticationHeader())
            .get(Map.class);
    }

    protected void setAuthentication(String accountId, String password) {
        this.accountId = accountId;
        this.password = password;
    }

    protected String getAuthenticationHeader() {
        if (accountId == null || password == null) {
            return "";
        }

        String payload = accountId + ":" + password;
        return "Basic " + DatatypeConverter.printBase64Binary(payload.getBytes());
    }
}
