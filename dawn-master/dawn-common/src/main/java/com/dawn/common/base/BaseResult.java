package com.dawn.common.base;

import cn.hutool.http.HttpStatus;
import lombok.Data;

/**
 * ---------------------------
 * 统一返回结果类 (BaseResult)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-08-08 13:45:00
 * ---------------------------
 */
@Data
public class BaseResult {

    private int code = HttpStatus.HTTP_OK;

    private String msg;

    private Object data;

    public static BaseResult error() {
        return error(HttpStatus.HTTP_INTERNAL_ERROR, "未知异常，请联系管理员");
    }

    public static BaseResult error(String msg) {
        return error(HttpStatus.HTTP_INTERNAL_ERROR, msg);
    }

    public static BaseResult error(int code, String msg) {
        BaseResult r = new BaseResult();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static BaseResult ok(String msg) {
        BaseResult r = new BaseResult();
        r.setMsg(msg);
        return r;
    }

    public static BaseResult ok(Object data) {
        BaseResult r = new BaseResult();
        r.setData(data);
        return r;
    }

    public static BaseResult ok(String msg, Object data) {
        BaseResult r = new BaseResult();
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
    public static BaseResult ok(int code, String msg, Object data) {
        BaseResult r = new BaseResult();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static BaseResult ok() {
        return new BaseResult();
    }

}
