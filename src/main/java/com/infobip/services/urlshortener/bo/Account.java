package com.infobip.services.urlshortener.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.infobip.services.urlshortener.validation.constraints.AccountId;

/**
 * Created by andrew
 */
public class Account extends GenericBusinessObject {
    @JsonProperty("accountId")
    @AccountId
    public String id;

    @JsonProperty("password")
    public String plainPassword;
}
