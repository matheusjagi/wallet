package com.picpay.walletservice.configs;

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

    @Value("${picpay.broker.exchange.movementEvent}")
    private String movementEventExchange;

    @Value("${picpay.broker.exchange.dlqExchange}")
    private String dlqExchange;

    @Value("${picpay.broker.queue.amountEventQueue.name}")
    private String updateAmountQueue;

    @Value("${picpay.broker.queue.amountEventQueue.dlq}")
    private String dlqUpdateAmount;

    @Value("${picpay.broker.key.updateAmountKey}")
    private String updateAmountKey;

    @Bean
    TopicExchange movementsExchange() {
        return new TopicExchange(movementEventExchange);
    }

    @Bean
    Queue updateAmountQueue() {
        return QueueBuilder
                .durable(updateAmountQueue)
                .deadLetterExchange(dlqExchange)
                .deadLetterRoutingKey(dlqUpdateAmount)
                .build();
    }

    @Bean
    Queue deadLetterQueueUpdateAmount() {
        return QueueBuilder.durable(dlqUpdateAmount).build();
    }

    @Bean
    Binding bindingUpdateAmount() {
        return BindingBuilder
                .bind(updateAmountQueue())
                .to(movementsExchange())
                .with(updateAmountKey);
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
