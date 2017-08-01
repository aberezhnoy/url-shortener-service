package com.infobip.services.urlshortener.services;

import com.infobip.services.urlshortener.bo.Account;

/**
 * Created by andrew
 *
 * Service to manage accounts
 */
public interface AccountService {
    /**
     * Create a new account
     * @param account to create
     * @throws Exception if account creation failed
     */
    public void create(Account account) throws Exception;

    /**
     * Retrieve account by accountId
     * @param id account id
     * @return account
     */
    public Account get(String id);
}
