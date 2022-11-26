package com.picpay.walletservice.publishers;

import com.picpay.walletservice.dtos.events.MovementEventDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisher {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${picpay.broker.exchange.movementEvent}")
    private String movementEventExchange;

    @Value("${picpay.broker.key.bankTransferKey}")
    private String movementEventKey;

    public void publisherWalletEvent(MovementEventDto movementEventDto) {
        rabbitTemplate.convertAndSend(movementEventExchange, movementEventKey, movementEventDto);
    }
}
