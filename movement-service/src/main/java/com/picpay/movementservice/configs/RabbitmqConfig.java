package com.picpay.movementservice.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
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

    @Value("${picpay.broker.exchange.dlqExchange}")
    private String dlqExchange;

    @Value("${picpay.broker.queue.bankTransferEventQueue.name}")
    private String bankTransferEventQueue;

    @Value("${picpay.broker.key.bankTransferKey}")
    private String bankTransferKey;

    @Value("${picpay.broker.queue.bankTransferEventQueue.dlq}")
    private String dlqBankTransfer;

    @Value("${picpay.broker.queue.paymentEventQueue.name}")
    private String paymentEventQueue;

    @Value("${picpay.broker.key.paymentKey}")
    private String paymentKey;

    @Value("${picpay.broker.queue.paymentEventQueue.dlq}")
    private String dlqPayment;

    @Bean
    TopicExchange movementsExchange() {
        return new TopicExchange(movementEventExchange);
    }

    @Bean
    TopicExchange dqlExchange() {
        return new TopicExchange(dlqExchange);
    }

    @Bean
    Queue paymentsQueue() {
        return QueueBuilder
                .durable(paymentEventQueue)
                .deadLetterExchange(dlqExchange)
                .deadLetterRoutingKey(dlqPayment)
                .build();
    }

    @Bean
    Queue bankTransferQueue() {
        return QueueBuilder
                .durable(bankTransferEventQueue)
                .deadLetterExchange(dlqExchange)
                .deadLetterRoutingKey(dlqBankTransfer)
                .build();
    }

    @Bean
    Queue deadLetterQueuePayment() {
        return QueueBuilder.durable(dlqPayment).build();
    }

    @Bean
    Queue deadLetterQueueBankTransfer() {
        return QueueBuilder.durable(dlqBankTransfer).build();
    }

    @Bean
    Binding bindingPayments() {
        return BindingBuilder
                .bind(paymentsQueue())
                .to(movementsExchange())
                .with(paymentKey);
    }

    @Bean
    Binding bindingBankTransfer() {
        return BindingBuilder
                .bind(bankTransferQueue())
                .to(movementsExchange())
                .with(bankTransferKey);
    }

    @Bean
    Binding bindingDlqPayments() {
        return BindingBuilder
                .bind(deadLetterQueuePayment())
                .to(dqlExchange())
                .with(dlqPayment);
    }

    @Bean
    Binding bindingDlqBankTransfer() {
        return BindingBuilder
                .bind(deadLetterQueueBankTransfer())
                .to(dqlExchange())
                .with(dlqBankTransfer);
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
