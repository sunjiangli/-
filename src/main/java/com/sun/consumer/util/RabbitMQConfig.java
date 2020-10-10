package com.sun.consumer.util;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
 
import java.util.HashMap;
import java.util.Map;
 
/**
 * @Description: RabbitMQ配置信息，绑定交换器、队列、路由键设置
 * @author: 孙江利
 * @Date: 2020/10/9
 * <p>
 * 说明：
 * <p>
 * 死信交换机（Dead-Letter-Exchange）: 当消息变成一个死信之后，如果这个消息所在的队列存在x-dead-letter-exchange参数，那么它会被发送到x-dead-letter-exchange对应值的交换器上
 * <p>
 * 使用方法：申明队列的时候设置 x-dead-letter-exchange 参数
 * <p>
 * 判断一个消息是否是死信消息（Dead Message）的依据：
 * a. 消息被拒绝（Basic.Reject或Basic.Nack）并且设置 requeue 参数的值为 false;
 * b. 消息过期; 消息过期时间设置主要有两种方式：
 *      1.设置队列的过期时间，这样该队列中所有的消息都存在相同的过期时间（在队列申明的时候使用 x-message-ttl 参数，单位为 毫秒）
 *      2.单独设置某个消息的过期时间，每条消息的过期时间都不一样；（设置消息属性的 expiration 参数的值，单位为 毫秒）
 *      3.如果同时使用了两种方式设置过期时间，以两者之间较小的那个数值为准；
 * c. 队列已满(队列满了，无法再添加数据到mq中);
 * <p>
 * 备份交换器(alternate-exchange)：未被正确路由的消息将会经过此交换器
 * 使用方法：申明交换器的时候设置 alternate-exchange 参数
 */
@Component
public class RabbitMQConfig {

    private static final String DEAD_LETTERS_QUEUE_NAME = "dead_letters_queue_name";
    private static final String DEAD_LETTERS_EXCHANGE_NAME = "dead_letters_exchange_name";
    private static final String QUEUE_NAME = "test_dlx_queue_name";
    private static final String EXCHANGE_NAME = "test_dlx_exchange_name";
    private static final String ROUTING_KEY = "order.add";
 
    /**
     * 声明备份队列、备份交换机、绑定队列到备份交换机
     * 建议使用FanoutExchange广播式交换机
     */
   /* @Bean
    public Queue msgBakQueue() {
        return new Queue(MESSAGE_BAK_QUEUE_NAME);
    }
 
    @Bean
    public FanoutExchange msgBakExchange() {
        return new FanoutExchange(MESSAGE_BAK_EXCHANGE_NAME);
    }
 
    @Bean
    public Binding msgBakBinding() {
        return BindingBuilder.bind(msgBakQueue()).to(msgBakExchange());
    }*/

    @Bean
    public Queue deadLettersQueue() {
        return new Queue(DEAD_LETTERS_QUEUE_NAME);
    }

    @Bean
    public FanoutExchange deadLettersExchange() {
        return new FanoutExchange(DEAD_LETTERS_EXCHANGE_NAME);
    }

    @Bean
    public Binding deadLettersBinding() {
        return BindingBuilder.bind(deadLettersQueue()).to(deadLettersExchange());
    }

    /**
     * 声明普通队列，并指定相应的备份交换机、死信交换机
     */
    @Bean
    public Queue queue() {
        Map<String, Object> arguments = new HashMap<>(10);
        //指定死信发送的Exchange
        arguments.put("x-dead-letter-exchange", DEAD_LETTERS_EXCHANGE_NAME);
        return new Queue(QUEUE_NAME, true, false, false, arguments);
    }

    @Bean
    public Exchange exchange() {
        return new DirectExchange(EXCHANGE_NAME, true, false, null);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY).noargs();
    }

    @Bean
    Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}