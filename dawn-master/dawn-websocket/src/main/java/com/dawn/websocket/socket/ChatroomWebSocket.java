package com.dawn.websocket.socket;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.dawn.websocket.constant.WebSocketConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
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
     * 离线人名
     */
    private List<String> offlineNames = WebSocketConsts.USER_NAMES_LIST;

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    private String userName;

    /**
     * 连接建立成功后调用的方法*/
    @OnOpen
    public void onOpen(Session session) {
        // 判断是否大于聊天室最大人数限制
        if (getOnlineCount() >= WebSocketConsts.CHATROOM_MAXIMUM) {
            closeSession(session);
            return;
        }
        this.session = session;
        this.userName = RandomUtil.randomEle(offlineNames);
        this.offlineNames.remove(this.userName);
        addOnlineCount();
        sendAllMessage(userName + "加入聊天室！当前在线人数为:" + getOnlineCount());
        webSocketSet.add(this);
        sendMessage(session, "欢迎加入聊天室╰(~▽~)╭，" + userName);
        log.info("有新连接加入！当前在线人数为:{}", getOnlineCount());
    }

    /**
     * 连接关闭后调用的方法
     */
    @OnClose
    public void onClose() {
        if (session != null) {
            webSocketSet.remove(this);
            this.offlineNames.add(this.userName);
            subOnlineCount();
            log.info("有连接关闭！当前在线人数为:{}", getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 消息内容
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        if (session != null && !StrUtil.isBlank(userName)) {
            log.info("来自客户端的消息:{}", userName + "：" + message);
            sendAllMessage(userName + "：" + message);
        }
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

    /**
     * 关闭连接
     * @param session
     */
    private void closeSession(Session session) {
        synchronized (this) {
            try {
                if (session.isOpen()) {
                    sendMessage(session,"当前聊天室已满员（ﾉ´д｀）");
                    session.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // -------------------------------------------------------------------------- Private method end

}
