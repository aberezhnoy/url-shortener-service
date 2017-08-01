package com.infobip.services.urlshortener.services.impl.injvm;

import com.infobip.services.urlshortener.bo.Account;
import com.infobip.services.urlshortener.providers.Qualifiers;
import com.infobip.services.urlshortener.providers.config.Configuration;
import com.infobip.services.urlshortener.providers.config.Keys;
import com.infobip.services.urlshortener.services.AccountService;
import com.infobip.services.urlshortener.services.impl.BusinessException;
import com.infobip.services.urlshortener.utils.StringUtils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by andrew
 */
public class AccountServiceImpl implements AccountService {
    private char[] validCharMap;
    private final ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();

    @Inject
    private Configuration config;

    @PostConstruct
    public void init() {
        validCharMap = StringUtils.getValid(config.getString(Keys.ACCOUNT_PASSWORD_CHARS));
    }

    @Override
    public void create(Account account) throws Exception {
        Account registeredAccount = accounts.putIfAbsent(account.id, account);

        if (registeredAccount != null) {
            throw new BusinessException("Account already exists", 409);
        }

        account.plainPassword = StringUtils.generate(validCharMap, config.getInteger(Keys.ACCOUNT_PASSWORD_LENGTH));
    }

    @Override
    public Account get(String id) {
        return accounts.get(id); // TODO: deep clone
    }
}
