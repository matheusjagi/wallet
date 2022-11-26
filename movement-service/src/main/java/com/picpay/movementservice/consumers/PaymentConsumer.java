package com.picpay.movementservice.consumers;

import com.picpay.movementservice.dtos.events.MovementEventDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class PaymentConsumer {

    @RabbitListener(queues = "${picpay.broker.queue.paymentEventQueue.name}")
    public void listen(@Payload MovementEventDto movementEventDto) {
        log.info("Chegou a mensagem na fila PAYMENT: {}", movementEventDto);
    }
}
