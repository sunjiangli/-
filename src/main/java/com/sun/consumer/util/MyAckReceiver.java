package com.sun.consumer.util;

import com.rabbitmq.client.Channel;

import com.sun.consumer.dao.OrderInfoDao;
import com.sun.consumer.entity.TLogUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class MyAckReceiver implements ChannelAwareMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    private OrderInfoDao orderInfoMapper;

    RabbitController  controller = new RabbitController ();

  /*  @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //因为传递消息的时候用的map传递,所以将Map从Message内取出需要做些处理
            String msg = message.toString();
            String[] msgArray = msg.split("'");//可以点进Message里面看源码,单引号直接的数据就是我们的map消息数据
            Map<String, String> msgMap = mapStringToMap(msgArray[1].trim(), 3);
            String messageId = msgMap.get("messageId");
            String messageData = msgMap.get("messageData");
            String createTime = msgMap.get("createTime");
            System.out.println("  MyAckReceiver  messageId:" + messageId + "  messageData:" + messageData + "  createTime:" + createTime);
            System.out.println("消费的主题消息来自：" + message.getMessageProperties().getConsumerQueue());
            channel.basicAck(deliveryTag, true);
			//channel.basicReject(deliveryTag, true);//为true会重新放回队列
        } catch (Exception e) {
            channel.basicReject(deliveryTag, false);
            e.printStackTrace();
        }
    }
  */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //因为传递消息的时候用的map传递,所以将Map从Message内取出需要做些处理


            if ("TestDirectQueue".equals(message.getMessageProperties().getConsumerQueue())) {
                String msg = message.toString();
                String[] msgArray = msg.split("'");//可以点进Message里面看源码,单引号直接的数据就是我们的map消息数据
                Map<String, String> msgMap = mapStringToMap(msgArray[1].trim(), 3);
                String messageId = msgMap.get("messageId");
                String messageData = msgMap.get("messageData");
                String createTime = msgMap.get("createTime");
                System.out.println("消费的消息来自的队列名为：" + message.getMessageProperties().getConsumerQueue());
                System.out.println("消息成功消费到  messageId:" + messageId + "  messageData:" + messageData + "  createTime:" + createTime);
                System.out.println("执行TestDirectQueue中的消息的业务处理流程......");

            }

            if ("fanout.A".equals(message.getMessageProperties().getConsumerQueue())) {
                String msg = message.toString();
                String[] msgArray = msg.split("'");//可以点进Message里面看源码,单引号直接的数据就是我们的map消息数据
                Map<String, String> msgMap = mapStringToMap(msgArray[1].trim(), 3);
                String messageId = msgMap.get("messageId");
                String messageData = msgMap.get("messageData");
                String createTime = msgMap.get("createTime");
                System.out.println("消费的消息来自的队列名为：" + message.getMessageProperties().getConsumerQueue());
                System.out.println("消息成功消费到  messageId:" + messageId + "  messageData:" + messageData + "  createTime:" + createTime);
                System.out.println("执行fanout.A中的消息的业务处理流程......");

            }
            if("testProduct".equals(message.getMessageProperties().getConsumerQueue())){
                String msg = message.toString();
                String[] msgArray = msg.split("'");//可以点进Message里面看源码,单引号直接的数据就是我们的map消息数据
                Map<String, String> msgMap = mapStringToMap(msgArray[1].trim(), 3);
                String userId  = msgMap.get("userId") ;
                    // System.out.println("用户{}开始抢单"+userId);
                    //处理消息
                    controller.robbingProduct(Integer.parseInt(userId));
                    //确认消息已经消费成功
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
            if("logQueue".equals(message.getMessageProperties().getConsumerQueue())){
                String msg = message.toString();
                String[] msgArray = msg.split("'");//可以点进Message里面看源码,单引号直接的数据就是我们的map消息数据
                Map<String, String> msgMap = mapStringToMap(msgArray[1].trim(), 3);
                //System.out.println("这个是用来记录日志的："+msgMap.get("log"));
                TLogUser tLogUser = new TLogUser() ;
                tLogUser.setUserid(Integer.parseInt(msgMap.get("userId")));
                tLogUser.setLog(msgMap.get("log"));
                tLogUser.setCreatetime(new Date());
                controller.insertLog(tLogUser) ;
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }

           /* if("test_dlx_queue_name".equals(message.getMessageProperties().getConsumerQueue())){
                //这里模拟随机拒绝一些消息到死信队列中
                if (new Random().nextInt(10) < 5) {
                    System.out.println("【Consumer】拒绝一条信息:[{}]，该消息将会被转发到死信交换器中"+msgMap.get("createTime"));
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
                } else {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                }
            }*/
            if("dead_letters_queue_name".equals(message.getMessageProperties().getConsumerQueue())){
                byte[] body = message.getBody();
                String orderId = new String(body, StandardCharsets.UTF_8);
                logger.info("消费者接收到订单：" + orderId);
                String orderStatus = orderInfoMapper.findByOrderStatus(orderId);
                logger.info("订单状态： " + orderStatus);
                if (!"1".equals(orderStatus)) {
                    //取消订单
                    orderInfoMapper.updateOrderStatus(orderId);
                }
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
			//channel.basicReject(deliveryTag, true);//为true会重新放回队列
        } catch (Exception e) {
//          拒绝当前消息，并把消息返回原队列   最
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            e.printStackTrace();
        }
    }

    //{key=value,key=value,key=value} 格式转换成map
    private Map<String, String> mapStringToMap(String str, int entryNum) {
        str = str.substring(1, str.length() - 1);
        String[] strs = str.split(",");
        Map<String, String> map = new HashMap<String, String>();
        for (String string : strs) {
            String key = string.split("=")[0].trim();
            String value = string.split("=")[1];
            map.put(key, value);
        }
        return map;
    }


}
