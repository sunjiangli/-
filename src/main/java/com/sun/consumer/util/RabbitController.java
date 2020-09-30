package com.sun.consumer.util;

import com.sun.consumer.dao.TLogUserDao;
import com.sun.consumer.dao.TProductDao;
import com.sun.consumer.dao.TProductRecordDao;
import com.sun.consumer.entity.TLogUser;
import com.sun.consumer.entity.TProduct;
import com.sun.consumer.entity.TProductRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RabbitController {

    @Autowired
    TProductDao tProductDao ;

    @Autowired
    TProductRecordDao tProductRecordDao ;

    @Autowired
    TLogUserDao tLogUserDao ;

    /**
     *   springboot 普通类无法调用dao
     *1  @Component注册该普通Java类
      2  @Autowired注册Dao层接口
      3  声明该类自身的静态类变量
      4  声明init()方法，进行初始化挂载；并用@PostConstruct注册 init()方法
      5  使用声明的该类的静态类变量，调用Dao层；
     *
     */
    public static RabbitController rabbitController;

    @PostConstruct
    public void init() {
        rabbitController = this;
        rabbitController.tProductDao = this.tProductDao;
        rabbitController.tProductRecordDao = this.tProductRecordDao;
        rabbitController.tLogUserDao = this.tLogUserDao;
    }



    public void robbingProduct(Integer userId){
        TProduct tProduct = rabbitController.tProductDao.queryById(1);
        TProductRecord tProductRecord = new TProductRecord();
        if (tProduct != null && tProduct.getTotal() > 0) {
            //更新库存表，库存量减少1。返回1说明更新成功。返回0说明库存已经为0
            tProduct.setTotal(tProduct.getTotal()-1);
            Integer total =rabbitController.tProductDao.update(tProduct);
            if(total>0){
                //插入记录
                tProductRecord.setProductno("No123321");
                tProductRecord.setUserid(userId);
                rabbitController.tProductRecordDao.insert(tProductRecord);
                //发送短信
                System.out.println("用户{}抢单成功"+userId);
            }else {
                System.out.println("用户{}抢单失败"+userId);

            }
        } else {
            System.out.println("用户{}抢单失败"+userId);
        }

    }

    //

    public  void insertLog(TLogUser tLogUser){
        rabbitController.tLogUserDao.insert(tLogUser);
    }

}
