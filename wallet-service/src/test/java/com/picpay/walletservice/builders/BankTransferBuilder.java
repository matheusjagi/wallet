package com.picpay.walletservice.builders;

import com.picpay.walletservice.dtos.BankTransferDto;
import org.springframework.stereotype.Component;

@Component
public class BankTransferBuilder {

    public static BankTransferDto build(String cpf,
                                        String password,
                                        Double operationAmount,
                                        String accountNumber,
                                        String accountAgency,
                                        String bankNumber) {
        return BankTransferDto.builder()
                .userCpf(cpf)
                .accountPassword(password)
                .operationAmount(operationAmount)
                .description("description")
                .accountNumber(accountNumber)
                .accountAgency(accountAgency)
                .bankNumber(bankNumber)
                .build();
    }
}
