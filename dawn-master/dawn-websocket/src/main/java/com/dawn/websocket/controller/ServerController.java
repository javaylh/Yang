package com.dawn.websocket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ---------------------------
 * 服务器监控Controller (ServerController)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-09-19 15:00:00
 * ---------------------------
 */
@RestController
@RequestMapping("/server")
public class ServerController {

    @GetMapping
    public String serverInfo() throws Exception {
        return "sada";
    }

}
