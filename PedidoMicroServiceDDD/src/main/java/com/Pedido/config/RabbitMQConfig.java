package com.Pedido.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter());
        return factory;
    }

    @Bean
    public Exchange pedidoDDDExchange() {
        return ExchangeBuilder.directExchange("pedidoDDDExchange").durable(true).build();
    }

    @Bean
    public Queue pedidoFeitoDDDQueue() {
        return new Queue("pedidoFeitoDDDQueue", true);
    }


    @Bean
    public Binding pedidoFeitoBinding(Queue pedidoFeitoDDDQueue, Exchange pedidoDDDExchange) {
        return BindingBuilder.bind(pedidoFeitoDDDQueue).to(pedidoDDDExchange).with("pedidoFeito").noargs();
    }

}

