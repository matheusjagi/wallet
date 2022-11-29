package com.picpay.timelineservice.consumers;

import com.picpay.timelineservice.dtos.events.TimelineEventListDto;
import com.picpay.timelineservice.models.TimelineDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class TimelineConsumer {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ModelMapper mapper;

    @RabbitListener(queues = "${picpay.broker.queue.timelineEventQueue.name}")
    public void listen(@Payload TimelineEventListDto timelineEventDto) {
        log.info("Message delivered to queue TIMELINE: {}", timelineEventDto);

        applicationEventPublisher.publishEvent(mapper.map(timelineEventDto, TimelineDocument.class));
    }
}
