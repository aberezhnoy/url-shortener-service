package com.infobip.services.urlshortener.providers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Created by andrew
 */
@Provider
public class JsonContextResolver implements ContextResolver<ObjectMapper> {
    private final ObjectMapper mapper;

    public JsonContextResolver() {
        mapper = new ObjectMapper();

        // deserialization
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // serialization
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public ObjectMapper getContext(Class<?> aClass) {
        return mapper;
    }
}
