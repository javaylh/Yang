package com.dawn.common.base;

import com.dawn.common.enums.ErrorCodeEnum;
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

    private String code = ErrorCodeEnum.OK.getCode();

    private String msg;

    private Object data;

    public static BaseResult error() {
        return error(ErrorCodeEnum.A0001.getCode(), "未知异常，请联系管理员");
    }

    public static BaseResult error(String msg) {
        return error(ErrorCodeEnum.A0001.getCode(), msg);
    }

    /**
     * 返回错误消息
     * @param errorCodeEnum 错误信息枚举类
     * @return 警告消息
     */
    public static BaseResult error(ErrorCodeEnum errorCodeEnum) {
        return error(errorCodeEnum.getCode(), errorCodeEnum.getMsg());
    }

    public static BaseResult error(String code, String msg) {
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

    public static BaseResult ok(String code, String msg, Object data) {
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
