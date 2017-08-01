package com.infobip.services.urlshortener.providers.security;

import com.infobip.services.urlshortener.bo.Account;
import com.infobip.services.urlshortener.services.AccountService;

import javax.inject.Inject;

/**
 * Created by andrew
 */
public class AuthenticationProviderImpl implements AuthenticationProvider {
    @Inject
    private AccountService accountService;

    @Override
    public boolean isAuthenticationValid(String accountId, String credentials) {
        Account foundAccount = accountService.get(accountId);

        return
            foundAccount != null &&
            foundAccount.plainPassword != null &&
            foundAccount.plainPassword.equals(credentials);
    }
}
