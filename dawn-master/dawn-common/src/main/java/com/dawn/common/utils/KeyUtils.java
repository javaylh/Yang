package com.dawn.common.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.Map;

/**
 * ---------------------------
 * 处理key的功能 (KeyUtils)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-09-17 16:30:00
 * ---------------------------
 */
public class KeyUtils {

    /**
     * 处理缓存使用的key
     * @param methodName
     * @param jsonStr
     * @return
     */
    public static String getKey(String methodName,String jsonStr){
        Map map =null;
        if (JSONUtil.isJson(jsonStr)){
            JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
            map = (Map) jsonObject;
        }
        return getKey(methodName,map);
    }

    /**
     * 处理缓存使用的key
     * @param methodName
     * @param map
     * @return
     */
    public static String getKey(String methodName, Map map){
        StringBuilder sb = getStringBuilder(methodName, map);
        return sb.toString();
    }

    /**
     * 获取缓存使用的key
     * @param methodName
     * @param map
     * @param pageNum
     * @return
     */
    public static String getKey(String methodName,Map map,Integer pageNum){
        StringBuilder sb = getStringBuilder(methodName, map);
        sb.append("-");
        sb.append(pageNum);
        return sb.toString();
    }

    /**
     * 同意处理字符串
     * @param methodName
     * @param map
     * @return
     */
    private static StringBuilder getStringBuilder(String methodName, Map map) {
        StringBuilder sb = new StringBuilder(methodName);
        if (!MapUtil.isEmpty(map)) {
            sb.append(JSONUtil.parseObj(map));
        }
        return sb;
    }

    /**
     * 获取缓存使用的key
     * @param methodName
     * @param map
     * @param pageNum
     * @param pageSize
     * @return
     */
    public static String getKey(String methodName,Map map,Integer pageNum,Integer pageSize){
        StringBuilder sb = getStringBuilder(methodName, map);
        sb.append("-");
        sb.append(pageNum);
        sb.append("-");
        sb.append(pageSize);
        return sb.toString();
    }

}
