package com.infobip.services.urlshortener.services.impl.injvm;

import com.infobip.services.urlshortener.bo.ShortUrl;
import com.infobip.services.urlshortener.providers.Qualifiers;
import com.infobip.services.urlshortener.providers.config.Configuration;
import com.infobip.services.urlshortener.providers.config.Keys;
import com.infobip.services.urlshortener.services.UrlShortenerService;
import com.infobip.services.urlshortener.services.impl.BusinessException;
import com.infobip.services.urlshortener.utils.UrlShortenerUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by andrew
 */
public class UrlShortenerServiceImpl implements UrlShortenerService {
    private ConcurrentHashMap<String, ShortUrl> urls = new ConcurrentHashMap<>();
    private ConcurrentMap<String, Map<String, ShortUrl>> userUrlsMapping = new ConcurrentHashMap<>();

    @Inject
    private Configuration config;

    @Override
    public void create(ShortUrl url) throws Exception {
        url.id = UrlShortenerUtils.shortUrl(url.accountId + ":" + url.url);

        if (url.redirectType == null) {
            url.redirectType = config.getInteger(Keys.REDIRECT_STATUS_DEFAULT);
        }

        if (urls.putIfAbsent(url.id, url) != null) {
            throw new BusinessException("Url already registered", 409);
        }

        userUrlsMapping
            .computeIfAbsent(url.accountId, urlId -> new ConcurrentHashMap<>())
            .put(url.id, url);
    }

    @Override
    public ShortUrl getByShortUrl(String id) {
        return urls.get(id);
    }

    @Override
    public List<ShortUrl> listByAccount(String accountId) {
        Map<String, ShortUrl> urlsOnAccount = userUrlsMapping.get(accountId);

        return urlsOnAccount == null ?
            new ArrayList<>():
            new ArrayList<>(urlsOnAccount.values());
    }
}
