package com.infobip.services.urlshortener.resources;

import com.infobip.services.urlshortener.bo.ShortUrl;
import com.infobip.services.urlshortener.providers.config.Configuration;
import com.infobip.services.urlshortener.providers.config.Keys;
import com.infobip.services.urlshortener.providers.Qualifiers;
import com.infobip.services.urlshortener.providers.security.Secured;
import com.infobip.services.urlshortener.services.StatisticsService;
import com.infobip.services.urlshortener.services.UrlShortenerService;
import com.infobip.services.urlshortener.validation.constraints.UrlShortId;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrew
 */
@Path("/")
@Secured
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class UrlResource {
    @Inject
    private UrlShortenerService urlShortenerService;

    @Inject
    private StatisticsService statisticsService;

    @Inject
    private Configuration config;

    @Context
    private SecurityContext securityContext;

    @GET
    @PermitAll
    @Path("{urlId}")
    public Response performRedirection(@Valid @UrlShortId @PathParam("urlId") String urlId) {
        ShortUrl url = urlShortenerService.getByShortUrl(urlId);

        if (url == null) {
            throw new NotFoundException("Url not found");
        }

        statisticsService.registerRedirection(url.id);

        return Response
            .status(url.redirectType)
            .header("Location", url.url)
            .build();
    }

    @POST
    @Path("register")
    public Map<String, Object> create(@Valid ShortUrl url) throws Exception {
        Map<String, Object> responseBean = new HashMap<>();

        url.accountId = securityContext.getUserPrincipal().getName();
        urlShortenerService.create(url);

        responseBean.put("shortUrl", config.getString(Keys.URL_BASE) + url.id);

        return responseBean;
    }
}
