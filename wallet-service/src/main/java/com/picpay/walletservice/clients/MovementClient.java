package com.picpay.walletservice.clients;

import com.picpay.walletservice.dtos.MovementDto;
import com.picpay.walletservice.exceptions.DepositOperationException;
import com.picpay.walletservice.exceptions.WithdrawalOperationException;
import com.picpay.walletservice.publishers.TimelineEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Log4j2
public class MovementClient {

    private final RestTemplate restTemplate;
    private final TimelineEventPublisher timelineEventPublisher;

    @Value("${picpay.api.url.movement}")
    private String REQUEST_URI_MOVEMENT;

    public void withdraw(MovementDto movementDto) throws HttpClientErrorException {
        String uri = UriComponentsBuilder.fromUriString(REQUEST_URI_MOVEMENT)
                .path("/picpay-movement")
                .path("/movements/financial-operations")
                .path("/withdraw")
                .toUriString();

        try {
            restTemplate.postForObject(uri, movementDto, MovementDto.class);
        } catch (HttpStatusCodeException error) {
            throw new WithdrawalOperationException();
        }
    }

    public void deposit(MovementDto movementDto) {
        String uri = UriComponentsBuilder.fromUriString(REQUEST_URI_MOVEMENT)
                .path("/picpay-movement")
                .path("/movements/financial-operations")
                .path("/deposit")
                .toUriString();

        try {
            restTemplate.postForObject(uri, movementDto, MovementDto.class);
        } catch (HttpStatusCodeException error) {
            throw new DepositOperationException();
        }
    }
}
