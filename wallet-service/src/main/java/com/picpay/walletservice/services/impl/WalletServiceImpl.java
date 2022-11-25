package com.picpay.walletservice.services.impl;

import com.picpay.walletservice.clients.MovementClient;
import com.picpay.walletservice.dtos.AccountDto;
import com.picpay.walletservice.dtos.FinancialOperationDto;
import com.picpay.walletservice.dtos.MovementDto;
import com.picpay.walletservice.enums.FinancialOperationType;
import com.picpay.walletservice.enums.MovementType;
import com.picpay.walletservice.services.AccountService;
import com.picpay.walletservice.services.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class WalletServiceImpl implements WalletService {

    private final MovementClient movementClient;
    private final AccountService accountService;

    @Override
    public void withdraw(FinancialOperationDto financialOperationDto) {
        AccountDto accountDto = accountService.findByUserCpf(financialOperationDto.getUserCpf());
        Double currentAmount = accountDto.getAmount() - financialOperationDto.getOperationAmount();

        MovementDto movementDto = createMovementDtoFinancialOperations(financialOperationDto, currentAmount,
                accountDto, FinancialOperationType.WITHDRAW);

        movementClient.withdraw(movementDto);
        accountService.updateAccountAmount(accountDto.getId(), currentAmount);
    }

    @Override
    public void deposit(FinancialOperationDto financialOperationDto) {
        AccountDto accountDto = accountService.findByUserCpf(financialOperationDto.getUserCpf());
        Double currentAmount = accountDto.getAmount() + financialOperationDto.getOperationAmount();

        MovementDto movementDto = createMovementDtoFinancialOperations(financialOperationDto, currentAmount,
                accountDto, FinancialOperationType.DEPOSIT);

        movementClient.deposit(movementDto);
        accountService.updateAccountAmount(accountDto.getId(), currentAmount);
    }

    @Override
    public AccountDto paymentInvoice(UUID accountId, AccountDto accountDto) {
        return null;
    }

    @Override
    public AccountDto bankTransfer(UUID accountSourceId, AccountDto accountTarget) {
        return null;
    }

    private static MovementDto createMovementDtoFinancialOperations(FinancialOperationDto financialOperationDto,
                                                                    Double currentAmount, AccountDto accountDto,
                                                                    FinancialOperationType financialOperationType) {
        return MovementDto.builder()
                .movementDate(LocalDateTime.now(ZoneId.of("UTC")))
                .description(financialOperationDto.getDescription())
                .transactionAmount(financialOperationDto.getOperationAmount())
                .type(MovementType.FINANCIAL_OPERATION)
                .previousAmount(accountDto.getAmount())
                .currentAmount(currentAmount)
                .accountId(accountDto.getId())
                .financialOperationType(financialOperationType)
                .build();
    }
}
