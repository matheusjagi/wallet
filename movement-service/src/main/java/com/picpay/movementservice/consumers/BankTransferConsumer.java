package com.picpay.movementservice.consumers;

import com.picpay.movementservice.dtos.MovementDto;
import com.picpay.movementservice.dtos.events.MovementEventDto;
import com.picpay.movementservice.services.BankTransferService;
import com.picpay.movementservice.services.utils.UtilsService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Log4j2
public class BankTransferConsumer {

    @Autowired
    private BankTransferService bankTransferService;

    @Autowired
    private ModelMapper mapper;

    @RabbitListener(queues = "${picpay.broker.queue.bankTransferEventQueue.name}")
    public void listen(@Payload MovementEventDto movementEventDto) throws Exception {
        log.info("Chegou a mensagem na fila BANK TRANSFER: {}", movementEventDto);

        Boolean messageFailed = UtilsService.createRandomBoolean();

        if (!messageFailed) {
            log.error("Message failed to listener bank transfer.");
            throw new RuntimeException("Message failed to listener bank transfer.");
        }

        bankTransferService.transfer(mapper.map(movementEventDto, MovementDto.class));
    }
}
