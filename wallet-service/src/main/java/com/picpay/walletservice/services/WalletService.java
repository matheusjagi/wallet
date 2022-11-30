package com.picpay.walletservice.services;

import com.picpay.walletservice.dtos.BankTransferDto;
import com.picpay.walletservice.dtos.FinancialOperationDto;
import com.picpay.walletservice.dtos.PaymentDto;

public interface WalletService {

    void withdraw(FinancialOperationDto financialOperationDto);

    void deposit(FinancialOperationDto financialOperationDto);

    void paymentInvoice(PaymentDto paymentDto);

    void bankTransfer(BankTransferDto bankTransferDto);
}
