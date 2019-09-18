package com.dawn.rabbitmq.constant;

/**
 * ---------------------------
 * 消息队列相关常量 (RabbitConsts)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-09-18 16:00:00
 * ---------------------------
 */
public class RabbitConsts {

    /**
     * 主题模式
     */
    public final static String TOPIC_MODE_QUEUE = "topic.mode";

    /**
     * 路由1：通配符 # ，代表一个或多个占位符，或者说一个或多个单词，那么 queue.email 可以匹配，queue.user.email 也可以匹配
     */
    public final static String TOPIC_ROUTING_KEY_ONE = "queue.#";

    /**
     * 路由2：通配符 * ，代表一个占位符，或者说一个单词，那么 user.queue 可以匹配，但是 user.aaa.queue 就匹配不了
     */
    public final static String TOPIC_ROUTING_KEY_TWO = "*.queue";

    /**
     * 路由3：路由必须为3.queue才能匹配
     */
    public final static String TOPIC_ROUTING_KEY_THREE = "3.queue";

    /**
     * 测试队列
     */
    public final static String QUEUE_TEST = "queue.test";

}
