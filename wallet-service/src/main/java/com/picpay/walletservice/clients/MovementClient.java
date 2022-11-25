package com.picpay.walletservice.clients;

import com.picpay.walletservice.dtos.AccountDto;
import com.picpay.walletservice.dtos.MovementDto;
import com.picpay.walletservice.enums.FinancialOperationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Log4j2
public class MovementClient {

    private final RestTemplate restTemplate;

    @Value("${picpay.api.url.movement}")
    private String REQUEST_URI_MOVEMENT;

    public void withdraw(MovementDto movementDto) {
        String url = REQUEST_URI_MOVEMENT + "/movements/financial-operation/withdraw";

        try {
            restTemplate.postForObject(url, movementDto, Void.class);
        } catch (HttpStatusCodeException error) {
            log.error("Error performing withdrawal operation.", error);
        }
    }

    public void deposit(MovementDto movementDto) {
        String url = REQUEST_URI_MOVEMENT + "/movements/financial-operation/deposit";

        try {
            restTemplate.postForObject(url, movementDto, Void.class);
        } catch (HttpStatusCodeException error) {
            log.error("Error performing deposit operation.", error);
        }
    }
}
