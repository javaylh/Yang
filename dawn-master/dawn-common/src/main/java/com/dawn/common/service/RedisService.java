package com.dawn.common.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.dawn.common.page.MybatisPageHelper;
import com.dawn.common.page.PageRequest;
import com.dawn.common.page.PageResult;
import com.dawn.common.utils.KeyUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * ---------------------------
 * 封装的redis方法 (RedisService)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-09-17 16:30:00
 * ---------------------------
 */
@Component
public class RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    //--------------------对key的操作--------------------

    /**
     * 给指定的key指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     */
    public Boolean expireKey(String key, long time) {
        if (time > 0) {
            return redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
        return false;
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public Long getExpireByKey(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    public void delKeys(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }

    //--------------------对key的字符串操作--------------------

    /**
     * 普通字符串获取
     *
     * @param key 键
     * @return 值
     */
    public Object getValue(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通字符串放入
     *
     * @param key   键
     * @param value 值
     */
    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 普通字符串放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public void setValue(String key, Object value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            setValue(key, value);
        }
    }

    /**
     * 普通字符串放入,值递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return 递增后的值
     */
    public Long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 普通字符串放入,值递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return 递减后的值
     */
    public Long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    //--------------------Map的操作--------------------

    /**
     * 根据hashKey获得hash,再根据key获得hash里的值
     *
     * @param hashKey hask的键 不能为null
     * @param key     键  不能为null
     * @return 值
     */
    public Object getValueByHashKey(String hashKey, String key) {
        return redisTemplate.opsForHash().get(hashKey, key);
    }

    /**
     * 根据hashKey获得hash
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> getHashByHashKey(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 根据key,将map里的值都放在hash里
     *
     * @param key 键
     * @param map 对应多个键值
     */
    public void setHashByHashKey(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 根据key,将map里的值都放在hash里,并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     */
    public void setHashByHashKey(String key, Map<String, Object> map, long time) {
        redisTemplate.opsForHash().putAll(key, map);
        if (time > 0) {
            expireKey(key, time);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param hashKey hask的键
     * @param key     键
     * @param value   值
     */
    public void setValueByHashKey(String hashKey, String key, Object value) {
        redisTemplate.opsForHash().put(hashKey, key, value);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建,并设置时间
     *
     * @param hashKey hask的键
     * @param key     键
     * @param value   值
     * @param time    时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     */
    public void setValueByHashKey(String hashKey, String key, Object value, long time) {
        redisTemplate.opsForHash().put(hashKey, key, value);
        if (time > 0) {
            expireKey(hashKey, time);
        }

    }

    /**
     * 删除hash表中的值
     *
     * @param hashKey hask的键 不能为null
     * @param key     键 可以使多个 不能为null
     */
    public Long delHashValue(String hashKey, Object... key) {
        return redisTemplate.opsForHash().delete(hashKey, key);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param hashKey hask的键 不能为null
     * @param key     键 不能为null
     * @return true 存在 false不存在
     */
    public Boolean hashHasKey(String hashKey, String key) {
        return redisTemplate.opsForHash().hasKey(hashKey, key);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param hashKey hask的键
     * @param key     键
     * @param by      要增加几(大于0)
     * @return 新增后的值
     */
    public Double hashValueIncr(String hashKey, String key, double by) {
        return redisTemplate.opsForHash().increment(hashKey, key, by);
    }

    /**
     * hash递减
     *
     * @param hashKey hask的键
     * @param key     键
     * @param by      要减少几(小于0)
     * @return 递减后的值
     */
    public Double hashValueDecr(String hashKey, String key, double by) {
        return redisTemplate.opsForHash().increment(hashKey, key, -by);
    }

    //--------------------对key的set(无序)的操作--------------------

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return Set
     */
    public Set<Object> getSetByKey(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public Boolean setHasKey(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    set键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long setSetValue(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    set键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long setSetValue(String key, long time, Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        if (time > 0) {
            expireKey(key, time);
        }
        return count;
    }
    /**
     * 根据set的key获取set缓存的长度
     *
     * @param key 键
     * @return 长度
     */
    public Long getSetSizeByKey(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public Long setValueRemoveByKey(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    //--------------------对key的zset(有序集合)操作--------------------

    /**
     * 向zset插入数据的分值
     *
     * @param key   键
     * @param value 值
     * @param score 分值
     */
    public Boolean setZsetValueAndScore(String key, Object value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return 长度
     */
    public Long getZsetSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public Long zSetRemoveByValue(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    //--------------------list操作,均为rightPush--------------------

    /**
     * 根据key获取list缓存的内容
     *
     * @param key   键
     * @param start 开始 0 是第一个元素
     * @param end   结束 -1代表所有值
     * @return 取出来的元素 总数 end-start+1
     */
    public List<Object> getListByKey(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return 长度
     */
    public Long getListSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return 值
     */
    public Object getListValueByIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public Long setList(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 将list放入缓存,并指定过期时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     */
    public Long setList(String key, Object value, long time) {
        if (time > 0) {
            expireKey(key, time);
        }
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 将list放入缓存,追加
     *
     * @param key   键
     * @param value 值
     */
    public Long setList(String key, List<Object> value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 将list放入缓存,追加
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     */
    public Long setList(String key, List<Object> value, long time) {
        if (time > 0) {
            expireKey(key, time);
        }
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     */
    public void updateListByIndex(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移除等于value的N个值
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public Long removeListVale(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    //--------------------自定义--------------------

    /**
     * 通用的处理分页缓存的方法
     * @param request
     * @param cacheName
     * @param mapper
     * @return
     */
    public PageResult currencyFindPage(PageRequest request, String cacheName, Object mapper) {
        String field = KeyUtils.getKey("findPage",request.getParam(), request.getPageNum(), request.getPageSize());
        return getPageResult(request, cacheName, mapper, "findList", field);
    }

    /**
     * 通用的处理分页缓存的方法
     * @param request
     * @param cacheName
     * @param mapper
     * @return
     */
    public PageResult currencyFindPage(PageRequest request, String cacheName, Object mapper,String methodName) {
        String field = KeyUtils.getKey(methodName,request.getParam(), request.getPageNum(), request.getPageSize());
        return getPageResult(request, cacheName, mapper, methodName, field);
    }

    /**
     * 获取分页的具体数据
     * @param request
     * @param cacheName
     * @param mapper
     * @param methodName
     * @param field
     * @return
     */
    private PageResult getPageResult(PageRequest request, String cacheName, Object mapper, String methodName, String field) {
        PageResult pageResult;
        if (hashHasKey(cacheName, field)) {
            pageResult = (PageResult) getValueByHashKey(cacheName, field);
            return pageResult;
        }
        pageResult = MybatisPageHelper.findPage(request, mapper, methodName, request.getParam());
        if (!BeanUtil.isEmpty(pageResult) && !CollUtil.isEmpty(pageResult.getContent())) {
            setValueByHashKey(cacheName, field, pageResult);
        }
        return pageResult;
    }

}
