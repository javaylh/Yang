package com.dawn.rabbitmq.send;

import com.dawn.rabbitmq.constant.RabbitConsts;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ---------------------------
 * 消息队列发送封装 (RabbitSend)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-09-18 16:30:00
 * ---------------------------
 */
@Component
public class RabbitSend {

    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    public void sendTest(String msg) {
        // convertAndSend(交换器名, 路由键, 消息体)
        this.rabbitmqTemplate.convertAndSend(RabbitConsts.TOPIC_MODE_QUEUE, RabbitConsts.TOPIC_ROUTING_KEY_ONE, msg);
    }

}
