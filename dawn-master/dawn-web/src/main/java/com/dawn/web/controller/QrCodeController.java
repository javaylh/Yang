package com.dawn.web.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.dawn.common.annotation.ApiVersion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * ---------------------------
 * 二维码 (QrCodeController)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-10-16 09:30:00
 * ---------------------------
 */
@Slf4j
@RestController
@RequestMapping("/qrCode")
public class QrCodeController {

	/**
	 * 生成二维码
     * @param response
	 * @return 输出流
	 */
    @ApiVersion(1)
	@GetMapping(value="/{version}/generateQRCode")
    @ResponseBody
	public void generateQRCode(HttpServletResponse response, @PathVariable String version) {
        try {
            OutputStream os = response.getOutputStream();
            QrCodeUtil.generate("https://blog.csdn.net/qq_37552993", 300, 300 , "jpg", os);
        } catch (IOException e) {
            log.error("IO exception when getting output stream", e);
        }
	}

    /**
     * 生成二维码
     * @return 二维码文件路径
     */
    @ApiVersion(2)
    @GetMapping(value="/{version}/generateQRCode")
    @ResponseBody
    public String generateQRCode(@PathVariable String version) {
        QrConfig config = new QrConfig(300, 300);
        // 设置边距，既二维码和背景之间的边距
        config.setMargin(3);
        // 设置前景色，既二维码颜色（青色）
        config.setForeColor(Color.CYAN.getRGB());
        // 设置背景色（灰色）
        config.setBackColor(Color.GRAY.getRGB());
        // 生成二维码到文件，也可以到流
        File file = QrCodeUtil.generate("http://hutool.cn/", config, FileUtil.file("e:/qrcode.jpg"));
        return file.getPath();
    }

}
