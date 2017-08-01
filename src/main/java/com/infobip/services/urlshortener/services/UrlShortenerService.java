package com.infobip.services.urlshortener.services;

import com.infobip.services.urlshortener.bo.ShortUrl;

import java.util.List;

/**
 * Created by andrew
 *
 * Holds URLs and perform shortening
 */
public interface UrlShortenerService {
    /**
     * Create and shortening URL
     * @param url url to be shortened
     * @throws Exception if creation failed
     */
    public void create(ShortUrl url) throws Exception;

    /**
     * Retrieve URL model by id (short URL)
     * @param id short URL
     * @return
     */
    public ShortUrl getByShortUrl(String id);

    /**
     * List all URLs registered on account
     * @param accountId account id
     * @return URLs
     */
    public List<ShortUrl> listByAccount(String accountId);
}
