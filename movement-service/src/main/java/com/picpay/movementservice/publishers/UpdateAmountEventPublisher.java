package com.picpay.movementservice.publishers;

import com.picpay.movementservice.dtos.events.UpdateAmountEventDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UpdateAmountEventPublisher {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${picpay.broker.exchange.movementEventExchange}")
    private String movementEventExchange;

    @Value("${picpay.broker.key.updateAmountKey}")
    private String updateAmountKey;

    public void publisher(UpdateAmountEventDto updateAmountEventDto) {
        rabbitTemplate.convertAndSend(movementEventExchange, updateAmountKey, updateAmountEventDto);
    }
}
