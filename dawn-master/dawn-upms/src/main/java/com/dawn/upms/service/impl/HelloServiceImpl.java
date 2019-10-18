package com.dawn.upms.service.impl;

import com.dawn.common.constant.AnnotationConsts;
import com.dawn.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Service;

/**
 * ---------------------------
 * hello实现类 (HelloServiceImpl)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-10-16 17:00:00
 * <dubbo:service/>：服务提供者暴露服务配置
 * 详细属性可去官网查看：http://dubbo.apache.org/zh-cn/docs/user/references/xml/dubbo-service.html
 * ---------------------------
 */
@Slf4j
@Service(version = AnnotationConsts.VERSION, methods = {@Method(name = "sayGoodbye", timeout = 250, retries = 0)})
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        System.out.println("提供者收到了sayHello的调用： " + name);
        sleepWhile();
        return "Annotation, hello " + name;
    }

    private void sleepWhile() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
