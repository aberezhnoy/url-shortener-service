package com.infobip.services.urlshortener.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.infobip.services.urlshortener.validation.constraints.RedirectType;
import com.infobip.services.urlshortener.validation.constraints.Url;

/**
 * Created by andrew
 */
public class ShortUrl extends GenericBusinessObject {
    @JsonProperty("shortUrl")
    public String id;
    public String accountId;

    @Url
    public String url;

    @RedirectType
    public Integer redirectType;
}
