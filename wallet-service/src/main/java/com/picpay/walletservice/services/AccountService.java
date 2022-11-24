package com.picpay.walletservice.services;

import com.picpay.walletservice.dtos.AccountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AccountService {
    Page<AccountDto> findAll(Pageable pageable);

    AccountDto findById(UUID accountId);

    AccountDto create(AccountDto accountDto);

    AccountDto updateTypeAccount(UUID accountId, AccountDto accountDto);

    void deleteById(UUID accountId);
}
