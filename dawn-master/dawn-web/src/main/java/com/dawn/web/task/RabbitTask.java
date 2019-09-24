package com.dawn.web.task;

import com.dawn.web.task.async.AsyncDawnTask;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * ---------------------------
 * 消息队列定时任务 (RabbitTask)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-09-18 17:08:00
 * ---------------------------
 */
@Component
@EnableAsync
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RabbitTask {

    private final AsyncDawnTask asyncDawnTask;

    /**
     * Dawn定时任务--消息队列
     * ${dawn.task.sendrabbit}：每隔1分钟执行一次
     * @return
     */
    @Scheduled(cron = "${dawn.task.sendrabbit}")
    public void taskSendRabbit() {
        asyncDawnTask.taskDawnExecutor();
    }

}
