package com.sun.consumer.controller;



import com.sun.consumer.entity.TProduct;
import com.sun.consumer.service.TProductService;
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


/**
 * (TProduct)表控制层
 *
 * @author makejava
 * @since 2020-09-27 10:45:55
 */
@RestController
@RequestMapping("tProduct")
public class TProductController {
    /**
     * 服务对象
     */
    @Autowired
    private TProductService tProductService;

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


}