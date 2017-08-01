package com.infobip.services.urlshortener;

import com.infobip.services.urlshortener.providers.GenericExceptionMapper;
import com.infobip.services.urlshortener.providers.JsonContextResolver;
import com.infobip.services.urlshortener.providers.ValidationExceptionMapper;
import com.infobip.services.urlshortener.providers.security.AuthenticationFilter;
import com.infobip.services.urlshortener.providers.security.Secured;
import com.infobip.services.urlshortener.utils.UrlShortenerUtils;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.annotation.security.PermitAll;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created by andrew
 */
public class Bootstrap {
    public static void main(String[] args) throws Exception {
        String port = System.getenv("PORT");
        if (port == null) {
            port = "8080";
        }

        URI uri = UriBuilder.fromUri("http://localhost:8080").port(Integer.valueOf(port)).build();
        ResourceConfig config = createResourceConfig();
        JettyHttpContainerFactory.createServer(uri, config, true);
    }

    public static ResourceConfig createResourceConfig() {
        ResourceConfig config = new ResourceConfig();
        config.setApplicationName("Infobip URL shortener service");

        config.register(JsonContextResolver.class);
        config.register(GenericExceptionMapper.class);
        config.register(ValidationExceptionMapper.class);

        config.register(new DynamicFeature() {
            @Override
            public void configure(ResourceInfo resourceInfo, FeatureContext featureContext) {
                if (
                    (resourceInfo.getResourceClass().isAnnotationPresent(Secured.class) ||
                    resourceInfo.getResourceMethod().isAnnotationPresent(Secured.class)) &&
                    !resourceInfo.getResourceMethod().isAnnotationPresent(PermitAll.class)
                ) {
                    featureContext.register(AuthenticationFilter.class);
                }
            }
        });

        config.register(new ApplicationBindings());
        config.packages(true, Bootstrap.class.getPackage().getName().concat(".resources"));

        return config;
    }
}
