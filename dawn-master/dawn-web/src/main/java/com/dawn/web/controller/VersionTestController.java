package com.dawn.web.controller;

import com.dawn.common.annotation.ApiVersion;
import com.dawn.common.constant.AnnotationConsts;
import com.dawn.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

/**
 * ---------------------------
 * 接口版本控制测试(VersionTestController)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-09-29 17:30:00
 * ---------------------------
 */
@Slf4j
@RestController
@RequestMapping("/versionTest")
public class VersionTestController {

    /**
     * <dubbo:reference/>：服务消费者引用服务配置
     * 详细属性可去官网查看：http://dubbo.apache.org/zh-cn/docs/user/references/xml/dubbo-reference.html
     * <dubbo:method/>：方法级配置
     * 详细属性可去官网查看：http://dubbo.apache.org/zh-cn/docs/user/references/xml/dubbo-method.html
     */
    @Reference(version = AnnotationConsts.VERSION)
    private HelloService service;

    @ApiVersion()
    @ResponseBody
    @GetMapping(value = "/{version}/hello")
    public String helloV1(@PathVariable String version) {
        log.info("start helloV1().......... ");
        return "hello from version:1";
    }

    @ApiVersion(2)
    @ResponseBody
    @GetMapping(value = "/{version}/hello")
    public String helloV2(@PathVariable String version) {
        log.info("start helloV2().......... ");
        return "hello from version:2";
    }

    @ApiVersion(5)
    @ResponseBody
    @GetMapping(value = "/{version}/hello")
    public String helloV5(@PathVariable String version) {
        log.info("start helloV5().......... ");
        return "hello from version:5";
    }

    @ResponseBody
    @GetMapping(value = "/hello")
    public String hello(String str) {
        return service.sayHello(str);
    }

}

