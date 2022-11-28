package com.picpay.timelineservice.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Autowired
    CachingConnectionFactory cachingConnectionFactory;

    @Value("${picpay.broker.exchange.movementEventExchange}")
    private String movementEventExchange;

    @Value("${picpay.broker.queue.timelineEventQueue.name}")
    private String timelineEventQueue;

    @Value("${picpay.broker.key.timelineKey}")
    private String timelineKey;

    @Bean
    TopicExchange movementsExchange() {
        return new TopicExchange(movementEventExchange);
    }

    @Bean
    Queue timelineQueue() {
        return QueueBuilder
                .durable(timelineEventQueue)
                .build();
    }

    @Bean
    Binding bindingTimeline() {
        return BindingBuilder
                .bind(timelineQueue())
                .to(movementsExchange())
                .with(timelineKey);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
