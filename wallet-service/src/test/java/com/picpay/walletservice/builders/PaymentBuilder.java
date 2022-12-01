package com.picpay.walletservice.builders;

import com.picpay.walletservice.dtos.PaymentDto;
import org.springframework.stereotype.Component;

@Component
public class PaymentBuilder {

    public static PaymentDto build(String cpf,
                                   String password,
                                   Double operationAmount) {
        return PaymentDto.builder()
                .userCpf(cpf)
                .accountPassword(password)
                .operationAmount(operationAmount)
                .description("description")
                .barcodeNumber("34191.09834 89775.140323 17339.090007 3 91550000082129")
                .build();
    }
}
