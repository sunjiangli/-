package com.sun.consumer.controller;



import com.sun.consumer.entity.OrderInfo;
import com.sun.consumer.entity.TProduct;
import com.sun.consumer.service.OrderInfoService;
import com.sun.consumer.service.TProductService;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;


/**
 * (TProduct)表控制层
 *
 * @author makejava
 * @since 2020-09-27 10:45:55
 */
@RestController
@RequestMapping("tProduct")
public class TProductController {

    private static final String EXCHANGE_NAME = "test_dlx_exchange_name";
    private static final String ROUTING_KEY = "user.add";
    private static final String UN_ROUTING_KEY = "user.delete";

    /**
     * 服务对象
     */
    @Autowired
    private TProductService tProductService;

    @Autowired
    private OrderInfoService orderInfoService ;

    @Autowired
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public TProduct selectOne(Integer id) {
        Map<String, Object> map = new HashMap<>();
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: lonelyDirectExchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        map.put("log" , "访问了send方法") ;
        map.put("userId", id);
        map.put("log" , "访问了selectOne方法") ;
        rabbitTemplate.convertAndSend("logExchange","logKey", map);
        return this.tProductService.queryById(id);
    }

    @GetMapping("/send")
    public String send(String userId){
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: lonelyDirectExchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        //记录日志
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        map.put("log" , "访问了send方法") ;
        map.put("userId", userId);
        rabbitTemplate.convertAndSend("logExchange","logKey", map);
        rabbitTemplate.convertAndSend("testProduct","testKey", map);
        return "发送消息成功";

    }

    @GetMapping("/sendTest")
    public void sendTest(){
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "测试死信";
        Map<String, Object> map = new HashMap<>();
        //记录日志
        map.put("messageData", messageData);
        map.put("messageId", messageId);
        map.put("log" , "访问了sendTest方法");
      /*  for (int i = 1; i <= 10; i++) {
            String message = "发送第" + i + "条消息.";
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            map.put("createTime", createTime);
            map.put("message" ,message) ;
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, map);
            System.out.println("【发送了一条能够正确被路由的消息】,exchange:[{}],routingKey:[{}],message:[{}]"+ EXCHANGE_NAME+ROUTING_KEY+message);
        }*/

        // 发送两条不能正确被路由的消息，该消息将会被转发到我们指定的备份交换器中
        for (int i = 1; i <= 2; i++) {
            String message = "不能正确被路由的消息" + i;
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, UN_ROUTING_KEY, message, new CorrelationData(UUID.randomUUID().toString()));
            System.out.println("【发送了第一条不能正确被路由的消息】,exchange:[{}],routingKey:[{}],message:[{}]"+EXCHANGE_NAME+UN_ROUTING_KEY+message);
        }
    }

    @GetMapping("/sendTest1")
    public void send() {
        MessagePostProcessor messagePostProcessor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            // 设置编码
            messageProperties.setContentEncoding("utf-8");
            // 设置过期时间(60秒过期)
            int expiration = 1000 * 60;
            messageProperties.setExpiration(String.valueOf(expiration));
            return message;
        };
        //模拟创建五条订单消息
        OrderInfo orderInfo = new OrderInfo() ;
        for (int i = 0; i < 1000; i++) {
            String orderId = String.valueOf(i);
            //订单初始化状态都为未支付
            orderInfo.setPkid(UUID.randomUUID().toString());
            orderInfo.setOrderId(String.valueOf(i));
            orderInfo.setOrderStatus("0");
            orderInfoService.insert(orderInfo);
            rabbitTemplate.convertAndSend("test_dlx_exchange_name", "order.add", orderId, messagePostProcessor, new CorrelationData(UUID.randomUUID().toString()));
        }
    }

}