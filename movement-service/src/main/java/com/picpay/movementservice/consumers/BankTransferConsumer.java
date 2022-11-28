package com.picpay.movementservice.consumers;

import com.picpay.movementservice.dtos.MovementDto;
import com.picpay.movementservice.dtos.events.MovementEventDto;
import com.picpay.movementservice.services.BankTransferService;
import com.picpay.movementservice.services.utils.UtilsService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class BankTransferConsumer {

    @Autowired
    private BankTransferService bankTransferService;

    @Autowired
    private ModelMapper mapper;

    @RabbitListener(queues = "${picpay.broker.queue.bankTransferEventQueue.name}")
    public void listen(@Payload MovementEventDto movementEventDto) {
        log.info("Message delivered to queue BANK TRANSFER: {}", movementEventDto);

        Boolean messageFailed = UtilsService.createRandomBoolean();

        if (!messageFailed) {
            log.error("Message failed to listener bank transfer.");
            throw new RuntimeException("Message failed to listener bank transfer.");
        }

        bankTransferService.transfer(mapper.map(movementEventDto, MovementDto.class));
    }
}
