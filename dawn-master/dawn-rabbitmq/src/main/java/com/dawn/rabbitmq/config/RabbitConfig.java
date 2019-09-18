package com.dawn.rabbitmq.config;

import com.dawn.rabbitmq.constant.RabbitConsts;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ---------------------------
 * 消息队列配置 (RabbitConfig)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-09-18 16:00:00
 * ---------------------------
 */
@Configuration
public class RabbitConfig {

    /**
     * 主题模式队列
     * <li>路由格式必须以 . 分隔，比如 user.email 或者 user.aaa.email</li>
     * <li>通配符 * ，代表一个占位符，或者说一个单词，比如路由为 user.*，那么 user.email 可以匹配，但是 user.aaa.email 就匹配不了</li>
     * <li>通配符 # ，代表一个或多个占位符，或者说一个或多个单词，比如路由为 user.#，那么 user.email 可以匹配，user.aaa.email 也可以匹配</li>
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(RabbitConsts.TOPIC_MODE_QUEUE);
    }

    @Bean
    public Queue queueTest(){
        return new Queue(RabbitConsts.QUEUE_TEST);
    }

    @Bean
    Binding bindingExchangeMessage(Queue queueTest, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueTest).to(topicExchange).with(RabbitConsts.TOPIC_ROUTING_KEY_ONE);
    }

}
