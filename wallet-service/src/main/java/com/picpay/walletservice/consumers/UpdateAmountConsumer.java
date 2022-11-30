package com.picpay.walletservice.consumers;

import com.picpay.walletservice.dtos.events.UpdateAmountEventDto;
import com.picpay.walletservice.services.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class UpdateAmountConsumer {

    @Autowired
    private AccountService accountService;

    @RabbitListener(queues = "${picpay.broker.queue.amountEventQueue.name}")
    public void listen(@Payload UpdateAmountEventDto updateAmountEventDto) {
        accountService.updateAccountAmount(updateAmountEventDto.getMovementType().name(),
                updateAmountEventDto.getAccountId(), updateAmountEventDto.getAmount());

        log.info("Update account amount. Account ID: {}", updateAmountEventDto.getAccountId());
    }
}
