package com.br.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jesse.zhang
 * created on 2021-10-27
 */
@Configuration
public class DelayQueueConfig {

    public static final String DELAY_EXCHANGE = "xDelayedExchange";
    public static final String DELAY_QUEUE = "xDelayedQueue";
    public static final String DELAY_ROUTING_KEY = "xDelayedRouteKey";

//    @Bean("delayExchange")
//    public Exchange delayExchange() {
//        Map<String, Object> args = new HashMap<>(1);
////       x-delayed-type    声明 延迟队列Exchange的类型
//        args.put("x-delayed-type", "direct");
//        return new CustomExchange(DELAY_EXCHANGE, "x-delayed-message",true, false,args);
//    }
//
//    @Bean("delayQueue")
//    public Queue delayQueue() {
//        return QueueBuilder.durable(DELAY_QUEUE).build();
//    }
//
//    /**
//     * 将延迟队列通过routingKey绑定到延迟交换器
//     *
//     * @return
//     */
//    @Bean
//    public Binding delayQueueBindExchange() {
//        return new Binding(DELAY_QUEUE, Binding.DestinationType.QUEUE, DELAY_EXCHANGE, DELAY_ROUTING_KEY, null);
//    }
}
