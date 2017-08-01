package com.infobip.services.urlshortener.services.impl.injvm;

import com.infobip.services.urlshortener.bo.ShortUrl;
import com.infobip.services.urlshortener.services.StatisticsService;
import com.infobip.services.urlshortener.services.UrlShortenerService;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by andrew
 */
public class StatisticsServiceImpl implements StatisticsService {
    private ConcurrentHashMap<String, AtomicInteger> stats = new ConcurrentHashMap<>();

    @Inject
    private UrlShortenerService urlShortenerService;

    @Override
    public void registerRedirection(String urlId) {
        stats
            .computeIfAbsent(urlId, _urlId -> new AtomicInteger(0))
            .incrementAndGet();
    }

    @Override
    public Map<String, Integer> getAccountStatistics(String accountId) {
        Map<String, Integer> result = new HashMap<>();
        List<ShortUrl> urlsOnAccount = urlShortenerService.listByAccount(accountId);

        for (ShortUrl url : urlsOnAccount) {
            AtomicInteger counterValue = stats.get(url.id);

            result.put(url.url, counterValue != null ? counterValue.get() : 0);
        }

        return result;
    }
}
