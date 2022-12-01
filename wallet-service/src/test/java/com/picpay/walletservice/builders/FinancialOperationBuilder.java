package com.picpay.walletservice.builders;

import com.picpay.walletservice.dtos.FinancialOperationDto;
import org.springframework.stereotype.Component;

@Component
public class FinancialOperationBuilder {

    public static FinancialOperationDto build(String cpf,
                                              String password,
                                              Double operationAmount,
                                              String accountNumber) {
        return FinancialOperationDto.builder()
                .userCpf(cpf)
                .accountPassword(password)
                .operationAmount(operationAmount)
                .description("description")
                .accountNumber(accountNumber)
                .accountAgency("0009")
                .bankNumber("999")
                .build();
    }
}
