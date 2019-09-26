package com.dawn.websocket.socket;

import com.dawn.websocket.constant.WebSocketConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * ---------------------------
 * WebSocket聊天室 (ChatroomWebSocket)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-09-26 15:20:00
 * ---------------------------
 */
@ServerEndpoint(value = WebSocketConsts.CHATROOM_SERVER)
@Component
@Slf4j
public class ChatroomWebSocket {

    // private static RedisService redisService = SpringUtils.getBean("redisService");

    /**
     * 在线连接数
     */
    private static int onlineCount = 0;

    /**
     * 每个客户端对应的MyWebSocket对象
     */
    private static CopyOnWriteArraySet<ChatroomWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 连接建立成功后调用的方法*/
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();
        log.info("有新连接加入！当前在线人数为:{}", getOnlineCount());
        sendAllMessage("有新连接加入！当前在线人数为:" + getOnlineCount());
    }

    /**
     * 连接关闭后调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        subOnlineCount();
        log.info("有连接关闭！当前在线人数为:{}", getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 消息内容
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:{}", message);
        sendAllMessage(message);
    }

    /**
     * 发生错误时调用的方法
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("chatroomWebSocket发生错误{}:", error.getMessage());
    }

    // -------------------------------------------------------------------------- Private method start

    /**
     * 向所有连接发送消息
     * @param message 消息内容
     */
    private void sendAllMessage(String message) {
        for (ChatroomWebSocket webSocket : webSocketSet) {
            this.sendMessage(webSocket.session, message);
        }
    }

    /**
     * 向某个连接发送消息
     * @param session
     * @param message 消息内容
     */
    private void sendMessage(Session session, String message) {
        synchronized (this) {
            try {
                // 若该session如果已被删除，则不执行发送请求，防止报错
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取在线连接数
     * @return int
     */
    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 在线连接数 +1
     */
    private static synchronized void addOnlineCount() {
        ChatroomWebSocket.onlineCount++;
    }

    /**
     * 在线连接数 -1
     */
    private static synchronized void subOnlineCount() {
        ChatroomWebSocket.onlineCount--;
    }

    // -------------------------------------------------------------------------- Private method end

}
