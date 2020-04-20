package com.dawn.web.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class Test {
    public static void main(String[] args) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        long id = snowflake.nextId();

        /**
         * add:
         * 1.新增一个目录（一级）：第一级
         * 查询数据所有一级(父id=null或者0) 最新一条数据（可根据sql：order by create_time desc limit 1）,记录不为空则截取其num+1,
         * 为空就说明是第一条直接ML0001
         *
         * 2.新增一个目录（x级）：子级 父级id=56(不要问我为什么是56，问就是脑海突然浮现)
         * 先查询出其父级id=56的目录的编码：假设ML0056，再查询表中所有父级编码为ML0056的最新一条数据（可根据sql：order by create_time desc limit 1）,
         * 记录不为空则截取其num+1,为空就说明是第一条直接父编码+0001
         *
         * delete:
         * 1.删除的话一定要删除本身及其子目录记录（可以先把所有要删的id都查出来，然后一条sql删除，避免循环删除）
         * DELETE * FROM tableName  WHERE id IN ('值一', '值二', ...);
         * 或
         * update tableName set 启用标识 = 不启用 WHERE id IN ('值一', '值二', ...);
         *
         * update:
         * 1.修改一个目录（一级）：第一级
         * 因为没有父级，所以不担心编码的问题，也不用管其子级，直接修改
         *
         * 2.修改了父级目录（很麻烦）0001到0003： 子级
         * ML00010020 --> ML0003xxxx(原先0020不知道是不是已经存在了，还要修改其所有子目录的编码，都改成xxxx)
         * 首先查询ML00030020存不存在：
         *  不存在：所有ML00030020子级或子级的子级，都要改编码（ML00030020开头）
         *  存在：（相当于新增一个）：
         *   查询其父级0001(父Id=)下最新的一条记录，num+1（xxxx）,所有ML0003xxxx的子级或子级的子级，都要改编码（ML0003xxxx开头）
         *
         *   还有缺点：有上限，有一条数据 ML0001 0001(很早之前有一个2级目录)--->ML00010001(很久之后有一个1级目录10001)
         */


    }
}
