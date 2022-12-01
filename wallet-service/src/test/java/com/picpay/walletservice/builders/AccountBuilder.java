package com.picpay.walletservice.builders;

import com.picpay.walletservice.enums.AccountStatus;
import com.picpay.walletservice.enums.AccountType;
import com.picpay.walletservice.models.AccountModel;
import com.picpay.walletservice.models.UserModel;
import com.picpay.walletservice.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Component
public class AccountBuilder {

    public static final Double INITIAL_AMOUNT = 1000D;

    @Autowired
    private AccountRepository accountRepository;

    private AccountModel build(UserModel user) {
        return AccountModel.builder()
                .number("4925999557")
                .agency("0009")
                .bankNumber("999")
                .password("1123")
                .amount(INITIAL_AMOUNT)
                .type(AccountType.SAVINGS)
                .status(AccountStatus.ACTIVE)
                .creationDate(LocalDateTime.now(ZoneId.of("UTC")))
                .lastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")))
                .user(user)
                .build();
    }

    public AccountModel create(UserModel user) {
        return accountRepository.save(build(user));
    }

    public AccountModel findById(UUID id) {
        return accountRepository.getReferenceById(id);
    }
}
