package com.dawn.web.task;

import com.dawn.web.task.async.AsyncDawnTask;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * ---------------------------
 * Dawn定时任务 (DawnTask)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-09-18 17:08:00
 * ---------------------------
 */
@Component
@EnableAsync
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DawnTask {

    private final AsyncDawnTask asyncDawnTask;

    /**
     * Dawn定时任务
     * 当前cron：每隔1分钟执行一次
     * cron = "${task.track.taskTrackCleaning}" 可写入配置文件中，易于维护
     * @return
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void taskSendRabbit() throws Exception {
        asyncDawnTask.taskDawnExecutor();
    }

}
