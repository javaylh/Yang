package com.dawn.websocket.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * ---------------------------
 * WebSocket相关常量 (WebSocketConsts)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-09-19 14:00:00
 * ---------------------------
 */
public class WebSocketConsts {

    /**
     * 聊天室WebSocket路径
     */
    public final static String CHATROOM_SERVER = "/webSocket/chatroom";

    /**
     * 聊天室容纳人数
     */
    public final static int CHATROOM_MAXIMUM = 4;

    /**
     * 聊天室随机人名List
     */
    public final static List<String> USER_NAMES_LIST = new ArrayList<String>() {
        {
            add("徐凤年");
            add("徐骁");
            add("吴素");
            add("Sylvia");
        }
    };

}
