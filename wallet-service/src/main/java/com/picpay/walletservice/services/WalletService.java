package com.picpay.walletservice.services;

import com.picpay.walletservice.dtos.AccountDto;
import com.picpay.walletservice.dtos.FinancialOperationDto;

import java.util.UUID;

public interface WalletService {

    void withdraw(FinancialOperationDto financialOperationDto);

    void deposit(FinancialOperationDto financialOperationDto);

    AccountDto paymentInvoice(UUID accountId, AccountDto accountDto);

    AccountDto bankTransfer(UUID accountSourceId, AccountDto accountTarget);
}
