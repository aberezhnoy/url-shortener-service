package com.infobip.services.urlshortener.services;

import java.util.Map;

/**
 * Created by andrew
 *
 * Holds URL redirection statistics
 */
public interface StatisticsService {
    /**
     * Register new redirection
     * @param urlId short URL
     */
    public void registerRedirection(String urlId);

    /**
     * Retrieve account statistics
     * @param accountId account id
     * @return statistics
     */
    public Map<String, Integer> getAccountStatistics(String accountId);
}
