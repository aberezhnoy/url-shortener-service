package com.infobip.services.urlshortener.resources;

import com.infobip.services.urlshortener.providers.security.Secured;
import com.infobip.services.urlshortener.services.StatisticsService;
import org.hibernate.validator.constraints.URL;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.Map;

/**
 * Created by andrew
 */
@Path("statistic")
@Secured
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class StatisticsResource {
    @Inject
    private StatisticsService statisticsService;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("{accountId}")
    public Map<String, Integer> getStatisticsForUser(@Valid @PathParam("accountId") String accountId) {
        if (!accountId.equals(securityContext.getUserPrincipal().getName())) {
            throw new ForbiddenException("Forbidden");
        }

        return statisticsService.getAccountStatistics(accountId);
    }
}
