package com.dawn.web.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.dawn.common.annotation.ApiVersion;
import com.dawn.common.constant.AnnotationConsts;
import com.dawn.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    @ResponseBody
    @GetMapping(value = "/export")
    public void export(HttpServletResponse response) throws IOException {
        // 构造数据：
        GroupCell groupCell1 = new GroupCell();
        groupCell1.setXtqymc("东安路区域");
        groupCell1.setXtjgjc("安吉斯鸿");

        GroupCell groupCell2 = new GroupCell();
        groupCell2.setXtqymc("东安路区域");
        groupCell2.setXtjgjc("安吉和鑫");

        GroupCell groupCell3 = new GroupCell();
        groupCell3.setXtqymc("东安路区域");
        groupCell3.setXtjgjc("安吉斯铭");

        GroupCell groupCell4 = new GroupCell();
        groupCell4.setXtqymc("康定路区域");
        groupCell4.setXtjgjc("安吉和鑫");

        GroupCell groupCell5 = new GroupCell();
        groupCell5.setXtqymc("汉中路区域");
        groupCell5.setXtjgjc("");

        // 1.需要导出的数据
        ArrayList<GroupCell> rows = CollUtil.newArrayList(groupCell1, groupCell2, groupCell3, groupCell4, groupCell5);

        // 2.根据区域名称进行分组 (保持rows导出数据的顺序)
        // {东安路区域=[GroupCell{Xtqymc='东安路区域', Xtjgjc=安吉斯鸿}, GroupCell{Xtqymc='东安路区域', Xtjgjc=安吉和鑫}, GroupCell{Xtqymc='东安路区域', Xtjgjc=安吉斯铭}}, 。。]}
        Map<String, List<GroupCell>> map = rows.stream().collect(Collectors.groupingBy(GroupCell::getXtqymc, LinkedHashMap::new, Collectors.toList()));

        // 写出数据：
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter();
        // 3.先导出所有数据
        writer.renameSheet("Sheet名字");
        readListToExcel(writer);
        // 只导出设置过addHeaderAlias的列
        writer.setOnlyAlias(true);
        writer.write(rows);
        // 4.动态合并列
        // 起始行从第二行开始（第一行是列名）
        int index = 1;
        for (Map.Entry<String, List<GroupCell>> stringListEntry : map.entrySet()) {
            if (stringListEntry.getValue().size() > 1) {
                String content = stringListEntry.getKey();
                // Column是区域名称的列
                int lastRow = index + stringListEntry.getValue().size() - 1;
                writer.merge(index, lastRow,0,0, content,false);
                index+=stringListEntry.getValue().size();
            } else {
                // 虽然没有业务单元或者只有一个，也默认占一行
                index++;
            }
        }
        // response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        // filename.xls是弹出下载对话框的文件名
        response.setHeader("Content-disposition", "attachment;filename=filename" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT) + ".xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out);
        // 关闭writer，释放内存
        writer.close();
    }

    /**
     * 把list中的map属性值自定义到excel的标题下
     * 职员
     * @param excelWriter
     * @return
     */
    public ExcelWriter readListToExcel(ExcelWriter excelWriter) {
        excelWriter
                .addHeaderAlias("xtqymc", "区域名称")
                .addHeaderAlias("xtjgjc", "业务单元");
        return excelWriter;
    }

}

