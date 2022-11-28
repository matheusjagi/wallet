package com.picpay.walletservice.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.picpay.walletservice.dtos.AccountDto;
import com.picpay.walletservice.dtos.BankTransferDto;
import com.picpay.walletservice.dtos.FinancialOperationDto;
import com.picpay.walletservice.dtos.PaymentDto;
import com.picpay.walletservice.services.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/wallets")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Log4j2
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody @Validated(FinancialOperationDto.FinancialOperationView.Withdraw.class)
                                               @JsonView(FinancialOperationDto.FinancialOperationView.Withdraw.class)
                                               FinancialOperationDto financialOperationDto) {
        log.debug("POST request to withdraw account: {}", financialOperationDto);
        walletService.withdraw(financialOperationDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody @Validated(FinancialOperationDto.FinancialOperationView.Deposit.class)
                                              @JsonView(FinancialOperationDto.FinancialOperationView.Deposit.class)
                                              FinancialOperationDto financialOperationDto) {
        log.debug("POST request to deposit account: {}", financialOperationDto);
        walletService.deposit(financialOperationDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/payment/invoice")
    public ResponseEntity<Void> paymentInvoice(@RequestBody @Valid PaymentDto paymentDto) {
        log.debug("POST request to payment invoice. {}", paymentDto);
        walletService.paymentInvoice(paymentDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transfer")
    @JsonView(AccountDto.AccountView.List.class)
    public ResponseEntity<AccountDto> bankTransfer(@RequestBody @Valid BankTransferDto bankTransferDto) {
        log.debug("POST request to transfer amount between accounts. {}", bankTransferDto);
        walletService.bankTransfer(bankTransferDto);
        return ResponseEntity.ok().build();
    }
}
