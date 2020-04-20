package com.dawn.upms.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.dawn.common.constant.AnnotationConsts;
import com.dawn.common.constant.CacheConsts;
import com.dawn.common.page.PageRequest;
import com.dawn.common.page.PageResult;
import com.dawn.common.service.RedisService;
import com.dawn.common.utils.KeyUtils;
import com.dawn.model.User;
import com.dawn.service.UserService;
import com.dawn.upms.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

/**
 * ---------------------------
 * User实现类 (UserServiceImpl)
 * ---------------------------
 * @author： ylh
 * @date： 2019-10-16 17:00:00
 * <dubbo:service/>：服务提供者暴露服务配置
 * 详细属性可去官网查看：http://dubbo.apache.org/zh-cn/docs/user/references/xml/dubbo-service.html
 * ---------------------------
 */
@Slf4j
@Service(version = AnnotationConsts.VERSION)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final RedisService redisService;

    private final UserMapper userDao;

    private final static String cacheName = CacheConsts.USER_CACHE;

    @Override
    @CacheEvict(value = cacheName,allEntries = true,beforeInvocation = true)
    public int save(User record) {
        redisService.delete(cacheName);
        if(record.getId() == null || record.getId() == 0) {
            return userDao.add(record);
        }
        return userDao.update(record);
    }

    @Override
    @CacheEvict(value = cacheName,allEntries = true,beforeInvocation = true)
    public int delete(User record) {
        redisService.delete(cacheName);
        return userDao.delete(record.getId());
    }

    @Override
    @CacheEvict(value = cacheName,allEntries = true,beforeInvocation = true)
    public int delete(List<User> records) {
        for(User record:records) {
            delete(record);
        }
        return 1;
    }

    @Override
    @Cacheable(value = cacheName,key = "'findById-'+#id",unless = "#result==null")
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
//	    PageResult pageResult = null;
//	    if (pageRequest.getParam() != null) {
//	        pageResult = MybatisPageHelper.findPage(pageRequest, userDao,"findList",pageRequest.getParam());
//	    } else {
//	         pageResult = MybatisPageHelper.findPage(pageRequest, userDao);
//	    }
//		return pageResult;
        return redisService.currencyFindPage(pageRequest,cacheName,userDao);
    }

    @Override
    public List<User> findList(Map map){
        String hashKey = KeyUtils.getKey("findList",map);
        if (redisService.hExists(cacheName,hashKey)){
            return (List<User>)redisService.hGet(cacheName,hashKey);
        }
        List<User> userList = userDao.findList(map);
        if (!CollUtil.isEmpty(userList)){
            redisService.hPut(cacheName,hashKey,userList);
        }
        return userList;
    }

}
