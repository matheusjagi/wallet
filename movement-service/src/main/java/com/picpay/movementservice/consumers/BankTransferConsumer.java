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
public class BankTransferConsumer {

    @RabbitListener(queues = "${picpay.broker.queue.bankTransferEventQueue.name}")
    public void listen(@Payload MovementEventDto movementEventDto) throws Exception {
        log.info("Chegou a mensagem na fila BANK TRANSFER: {}", movementEventDto);
        throw new RuntimeException("Failed");
    }
}
