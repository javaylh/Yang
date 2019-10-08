package com.dawn.web.controller;

import com.dawn.common.annotation.ApiVersion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/versionTest")
public class VersionTestController {

    @RequestMapping(value = "/{version}/hello", method = RequestMethod.GET)
    @ApiVersion(1)
    @ResponseBody
    public String helloV1() {
        log.info("start helloV1().......... ");
        return "hello from version:1";
    }

    @RequestMapping(value = "/{version}/hello", method = RequestMethod.GET)
    @ApiVersion(2)
    @ResponseBody
    public String helloV2() {
        log.info("start helloV2().......... ");
        return "hello from version:2";
    }

    @RequestMapping(value = "/{version}/hello", method = RequestMethod.GET)
    @ApiVersion(5)
    @ResponseBody
    public String helloV5() {
        log.info("start helloV5().......... ");
        return "hello from version:5";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String helloV() {
        return "hello！！！！！！";
    }

}

