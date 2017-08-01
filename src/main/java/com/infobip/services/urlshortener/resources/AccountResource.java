package com.infobip.services.urlshortener.resources;

import com.infobip.services.urlshortener.bo.Account;
import com.infobip.services.urlshortener.providers.security.Secured;
import com.infobip.services.urlshortener.services.AccountService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrew
 */
@Path("account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class AccountResource {
    @Inject
    private AccountService accountService;

    @POST
    public Map<String, Object> create(@Valid Account account) throws Exception {
        Map<String, Object> responseBean = new HashMap<>();

        accountService.create(account);

        responseBean.put("success", true);
        responseBean.put("description", "Account has been created");
        responseBean.put("password", account.plainPassword);

        return responseBean;
    }
}
