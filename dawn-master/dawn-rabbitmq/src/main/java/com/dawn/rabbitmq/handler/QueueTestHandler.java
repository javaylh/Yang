package com.dawn.rabbitmq.handler;

import com.dawn.rabbitmq.constant.RabbitConsts;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * ---------------------------
 * 测试队列 处理器 (QueueTwoHandler)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-09-18 16:45:00
 * ---------------------------
 */
@Slf4j
@RabbitListener(queues = RabbitConsts.QUEUE_TEST)
@Component
public class QueueTestHandler {

    @RabbitHandler
    public void directHandlerManualAck(String msg, Message message, Channel channel) {
        //  如果手动ACK确认,消息会被监听消费,但是消息在队列中依旧存在,如果 未配置 acknowledge-mode 默认是会在消费完毕后自动ACK掉
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("测试队列，手动ACK，接收消息：{}", msg);
            // 通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            try {
                // 处理失败,重新压入MQ
                channel.basicRecover();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
