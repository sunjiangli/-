package com.sun.consumer.util;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DirectRabbitConfig {



    public Queue TestDirectQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        // return new Queue("TestDirectQueue",true,true,false);
        //一般设置一下队列的持久化就好,其余两个就是默认false
        Map<String, Object> arguments = new HashMap<>(10);
        //指定死信发送的Exchange
        arguments.put("x-dead-letter-exchange", "back_logExchange");
        return new Queue("logQueue", true, false, false, arguments);
    }
    //Direct交换机 起名：TestDirectExchange
    @Bean
    DirectExchange TestDirectExchange() {
        return new DirectExchange("logExchange",true,false);
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    Binding bindingDirect4() {
        return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with("logKey");
    }

    /**
     * 声明死信交换机
     * 建议使用FanoutExchange广播式交换机
     */
    @Bean
    public Queue msgBakQueueLog() {
        return new Queue("back_logQueue");
    }

    @Bean
    public FanoutExchange msgBakExchangeLog() {
        return new FanoutExchange("back_logExchange");
    }

    @Bean
    public Binding msgBakBindingLog() {
        return BindingBuilder.bind(msgBakQueueLog()).to(msgBakExchangeLog());
    }






}
