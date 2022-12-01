package com.picpay.walletservice.services;

import com.picpay.walletservice.dtos.AccountDto;
import com.picpay.walletservice.enums.MovementType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AccountService {
    Page<AccountDto> findAll(Pageable pageable);

    AccountDto findById(UUID accountId);

    AccountDto findByUserCpf(String cpf);

    AccountDto create(AccountDto accountDto);

    AccountDto updateTypeAccount(UUID accountId, AccountDto accountDto);

    void deleteById(UUID accountId);

    void updateAccountAmount(String movementType, UUID accountId, Double amount);

    AccountDto findByAccountInformation(String accountNumber, String accountAgency, String bankNumber);
}
