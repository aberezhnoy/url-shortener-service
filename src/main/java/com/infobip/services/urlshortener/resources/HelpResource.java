package com.infobip.services.urlshortener.resources;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * Created by andrew
 */
@Path("help")
@Produces(MediaType.TEXT_HTML)
@Singleton
public class HelpResource {
    @GET
    public Response get() {
        InputStream is = HelpResource.class.getClassLoader().getResourceAsStream("help.html");

        return Response
            .status(Response.Status.OK)
            .header("Content-Type", "text/html; charset=UTF-8")
            .entity(is)
            .build();
    }
}
