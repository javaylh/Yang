package com.dawn.web.task.async;

import cn.hutool.core.util.NumberUtil;
import com.dawn.rabbitmq.send.RabbitSend;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * ---------------------------
 * 异步处理Dawn定时任务 (AsyncDawnTask)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-09-18 17:10:00
 * ---------------------------
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AsyncDawnTask {

    private final RabbitSend send;

    /**
     * 定时发送rabbitmq队列
     */
    @Async("taskExecutor")
    public void taskDawnExecutor() {
        send.sendTest("test" + NumberUtil.generateRandomNumber(0, 1000, 1)[0]);
    }

}
