package com.infobip.services.urlshortener;

import com.infobip.services.urlshortener.providers.Qualifiers;
import com.infobip.services.urlshortener.providers.config.Configuration;
import com.infobip.services.urlshortener.services.AccountService;
import com.infobip.services.urlshortener.providers.security.AuthenticationProvider;
import com.infobip.services.urlshortener.services.StatisticsService;
import com.infobip.services.urlshortener.services.UrlShortenerService;
import com.infobip.services.urlshortener.services.impl.injvm.AccountServiceImpl;
import com.infobip.services.urlshortener.providers.security.AuthenticationProviderImpl;
import com.infobip.services.urlshortener.services.impl.injvm.StatisticsServiceImpl;
import com.infobip.services.urlshortener.services.impl.injvm.UrlShortenerServiceImpl;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Created by andrew
 */
public class ApplicationBindings extends AbstractBinder {
    public static final String CONFIG_FILENAME = "config.properties";

    @Override
    protected void configure() {
        // configuration
        configuration();

        // security
        security();

        // service bindings
        services();
    }

    private void configuration() {
        TypeLiteral<Map<String, Object>> type = new TypeLiteral<Map<String, Object>>() {};
        Properties config = new Properties();

        try {
            config.load(ApplicationBindings.class.getClassLoader().getResourceAsStream(CONFIG_FILENAME));
        } catch (IOException e) {
            throw new RuntimeException("Error during loading configuration", e);
        }

        bind(config).to(type).named(Qualifiers.CONFIG);
        bind(new Configuration(config)).to(Configuration.class);

    }

    private void security() {
        bind(AuthenticationProviderImpl.class).to(AuthenticationProvider.class).in(Singleton.class);
    }

    private void services() {
        bind(AccountServiceImpl.class).to(AccountService.class).in(Singleton.class);
        bind(UrlShortenerServiceImpl.class).to(UrlShortenerService.class).in(Singleton.class);
        bind(StatisticsServiceImpl.class).to(StatisticsService.class).in(Singleton.class);
    }
}
