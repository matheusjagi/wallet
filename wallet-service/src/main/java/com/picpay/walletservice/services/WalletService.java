package com.picpay.walletservice.services;

import com.picpay.walletservice.dtos.AccountDto;
import com.picpay.walletservice.dtos.FinancialOperationDto;
import com.picpay.walletservice.dtos.PaymentDto;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public interface WalletService {

    void withdraw(FinancialOperationDto financialOperationDto);

    void deposit(FinancialOperationDto financialOperationDto);

    void paymentInvoice(PaymentDto paymentDto);

    AccountDto bankTransfer(UUID accountSourceId, AccountDto accountTarget);
}
