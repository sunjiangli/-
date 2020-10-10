package com.sun.consumer.util;

import com.rabbitmq.client.Channel;
import com.sun.consumer.dao.OrderInfoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

//@Component
public class Consumer {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    private OrderInfoDao orderInfoMapper;

    @RabbitListener(queues = "dead_letters_queue_name")
    public void handler(Message message, Channel channel) throws IOException {

        /**
         * 发送消息之前，根据订单ID去查询订单的状态，如果已支付不处理，如果未支付，则更新订单状态为取消状态。
         */
        // 从队列中取出订单号
        byte[] body = message.getBody();
        String orderId = new String(body, StandardCharsets.UTF_8);
        logger.info("消费者接收到订单：" + orderId);
        String orderStatus = orderInfoMapper.findByOrderStatus(orderId);
        logger.info("订单状态： " + orderStatus);
        if (!"1".equals(orderStatus)) {
            //取消订单
            orderInfoMapper.updateOrderStatus(orderId);
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }

}
