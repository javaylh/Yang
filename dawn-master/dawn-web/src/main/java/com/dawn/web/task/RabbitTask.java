package com.dawn.web.task;

import com.dawn.common.service.RedisService;
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

    private final RedisService redisService;

    private Integer count = 0;

    /**
     * Dawn定时任务--消息队列
     * ${dawn.task.send-rabbit}：每隔1分钟执行一次
     * @return
     */
    @Scheduled(cron = "${dawn.task.send-rabbit}")
    public void taskSendRabbit() {
        // 这个就是判断这个count在redis里面是否存在
        if (redisService.hasKey("count")) {
            count = Integer.valueOf(redisService.getValue("count").toString());
            count++;
        }
        redisService.setValue("count", count.toString());
        asyncDawnTask.taskDawnExecutor();
    }

}
