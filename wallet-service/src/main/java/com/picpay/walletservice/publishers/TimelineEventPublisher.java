package com.picpay.walletservice.publishers;

import com.picpay.walletservice.dtos.events.TimelineEventDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TimelineEventPublisher {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${picpay.broker.exchange.movementEvent}")
    private String movementEventExchange;

    @Value("${picpay.broker.key.timelineKey}")
    private String timelineKey;

    public void publisher(TimelineEventDto timelineEventDto) {
        rabbitTemplate.convertAndSend(movementEventExchange, timelineKey, timelineEventDto);
    }
}
