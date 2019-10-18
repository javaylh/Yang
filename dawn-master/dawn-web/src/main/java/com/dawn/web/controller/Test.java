package com.dawn.web.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class Test {
    public static void main(String[] args) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        long id = snowflake.nextId();
    }
}
