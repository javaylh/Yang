package com.dawn.web.task;

import cn.hutool.json.JSONUtil;
import com.dawn.websocket.constant.WebSocketConsts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * ---------------------------
 * WebSocket定时任务 (WebSocketTask)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-09-20 15:30:00
 * ---------------------------
 */
@Component
@EnableAsync
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebSocketTask {

    private final SimpMessagingTemplate wsTemplate;

    /**
     * Dawn定时任务--WebSocket
     * ${dawn.task.sendwebsocket}：每隔2秒执行一次
     * @return
     */
    @Scheduled(cron = "${dawn.task.sendwebsocket}")
    public void taskSendWebSocket() {
        wsTemplate.convertAndSend(WebSocketConsts.PUSH_SERVER, JSONUtil.toJsonStr("xz"));
    }

}
