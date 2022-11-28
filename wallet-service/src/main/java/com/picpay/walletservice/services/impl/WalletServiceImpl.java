package com.picpay.walletservice.services.impl;

import com.picpay.walletservice.clients.MovementClient;
import com.picpay.walletservice.dtos.AccountDto;
import com.picpay.walletservice.dtos.BankTransferDto;
import com.picpay.walletservice.dtos.FinancialOperationDto;
import com.picpay.walletservice.dtos.MovementDto;
import com.picpay.walletservice.dtos.PaymentDto;
import com.picpay.walletservice.dtos.events.MovementEventDto;
import com.picpay.walletservice.enums.FinancialOperationType;
import com.picpay.walletservice.enums.MovementType;
import com.picpay.walletservice.publishers.BankTransferEventPublisher;
import com.picpay.walletservice.publishers.PaymentEventPublisher;
import com.picpay.walletservice.services.AccountService;
import com.picpay.walletservice.services.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    private final ModelMapper mapper;
    private final PaymentEventPublisher paymentEventPublisher;
    private final BankTransferEventPublisher bankTransferEventPublisher;

    @Override
    public void withdraw(FinancialOperationDto financialOperationDto) {
        AccountDto accountDto = accountService.findByUserCpf(financialOperationDto.getUserCpf());
        accountService.checkPassword(accountDto.getId(), financialOperationDto.getAccountPassword());

        Double currentAmount = accountDto.getAmount() - financialOperationDto.getOperationAmount();
        checkEnoughBalance(currentAmount);

        MovementDto movementDto = createMovementDto(financialOperationDto, currentAmount, accountDto,
                FinancialOperationType.WITHDRAW);

        movementClient.withdraw(movementDto);

        accountService.updateAccountAmount(FinancialOperationType.WITHDRAW.name(), accountDto.getId(),
                financialOperationDto.getOperationAmount());
    }

    private void checkEnoughBalance(Double currentAmount) {
        if (currentAmount < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance to carry out the transaction");
        }
    }

    @Override
    public void deposit(FinancialOperationDto financialOperationDto) {
        AccountDto accountDto = accountService.findByAccountInformation(financialOperationDto.getAccountNumber(),
                financialOperationDto.getAccountAgency(), financialOperationDto.getBankNumber());
        Double currentAmount = accountDto.getAmount() + financialOperationDto.getOperationAmount();

        MovementDto movementDto = createMovementDto(financialOperationDto, currentAmount, accountDto,
                FinancialOperationType.DEPOSIT);

        movementClient.deposit(movementDto);

        accountService.updateAccountAmount(FinancialOperationType.DEPOSIT.name(), accountDto.getId(),
                financialOperationDto.getOperationAmount());
    }

    @Override
    public void paymentInvoice(PaymentDto paymentDto) {
        AccountDto accountDto = accountService.findByUserCpf(paymentDto.getUserCpf());
        accountService.checkPassword(accountDto.getId(), paymentDto.getAccountPassword());

        Double currentAmount = accountDto.getAmount() - paymentDto.getOperationAmount();
        checkEnoughBalance(currentAmount);

        MovementEventDto movementEventDto = mapper.map(createMovementDto(paymentDto, currentAmount, accountDto),
                MovementEventDto.class);

        paymentEventPublisher.publisher(movementEventDto);
    }

    @Override
    public void bankTransfer(BankTransferDto bankTransferDto) {
        AccountDto accountSourceDto = accountService.findByUserCpf(bankTransferDto.getUserCpf());
        accountService.checkPassword(accountSourceDto.getId(), bankTransferDto.getAccountPassword());

        Double currentAmount = accountSourceDto.getAmount() - bankTransferDto.getOperationAmount();
        checkEnoughBalance(currentAmount);

        AccountDto accountTargetDto = accountService.findByAccountInformation(bankTransferDto.getAccountNumber(),
                bankTransferDto.getAccountAgency(), bankTransferDto.getBankNumber());

        MovementEventDto movementEventDto = mapper.map(createMovementDto(bankTransferDto, currentAmount, accountSourceDto,
                        accountTargetDto.getId()), MovementEventDto.class);

        bankTransferEventPublisher.publisher(movementEventDto);
    }

    private MovementDto createMovementDto(FinancialOperationDto financialOperationDto,
                                          Double currentAmount, AccountDto accountDto,
                                          FinancialOperationType financialOperationType) {
        return MovementDto.builder()
                .movementDate(LocalDateTime.now(ZoneId.of("UTC")))
                .description(financialOperationDto.getDescription())
                .transactionAmount(financialOperationDto.getOperationAmount())
                .type(MovementType.FINANCIAL_OPERATION)
                .previousAmount(accountDto.getAmount())
                .currentAmount(currentAmount)
                .sourceAccountId(accountDto.getId())
                .financialOperationType(financialOperationType)
                .build();
    }

    private MovementDto createMovementDto(PaymentDto paymentDto, Double currentAmount, AccountDto accountDto) {
        return MovementDto.builder()
                .movementDate(LocalDateTime.now(ZoneId.of("UTC")))
                .description(paymentDto.getDescription())
                .transactionAmount(paymentDto.getOperationAmount())
                .type(MovementType.PAYMENT)
                .previousAmount(accountDto.getAmount())
                .currentAmount(currentAmount)
                .sourceAccountId(accountDto.getId())
                .barcodeNumber(paymentDto.getBarcodeNumber())
                .build();
    }

    private MovementDto createMovementDto(BankTransferDto bankTransferDto, Double currentAmount, AccountDto accountSourceDto ,
                                          UUID targetAccountId) {
        return MovementDto.builder()
                .movementDate(LocalDateTime.now(ZoneId.of("UTC")))
                .description(bankTransferDto.getDescription())
                .transactionAmount(bankTransferDto.getOperationAmount())
                .type(MovementType.BANK_TRANSFER)
                .previousAmount(accountSourceDto.getAmount())
                .currentAmount(currentAmount)
                .sourceAccountId(accountSourceDto.getId())
                .targetAccountId(targetAccountId)
                .build();
    }
}
