//package com.dawn.web.controller;
//
//import cn.hutool.core.bean.BeanUtil;
//import cn.hutool.core.date.DateTime;
//import com.dawn.common.annotation.ApiVersion;
//import com.dawn.common.base.BaseResult;
//import com.dawn.common.constant.AnnotationConsts;
//import com.dawn.common.constant.ConfigConsts;
//import com.dawn.common.page.PageRequest;
//import com.dawn.common.page.PageResult;
//import com.dawn.common.utils.I18nUtils;
//import com.dawn.model.User;
//import com.dawn.service.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.dubbo.config.annotation.Reference;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * ---------------------------
// * 用户管理(UserController)
// * ---------------------------
// * @author： ylh
// * @date： 2020-04-20 15:30:00
// * ---------------------------
// */
//@Slf4j
//@RestController
//@RequestMapping("/user")
//public class UserController {
//
//    @Reference(version = AnnotationConsts.VERSION)
//    private UserService userService;
//
//    /**
//     * 新增修改用户
//     * @param record
//     * @return
//     */
//    @ApiVersion()
//    @PostMapping(value="/{version}/save")
//    public BaseResult save(@RequestBody User record) {
//        CheckUser checkUser = new CheckUser(record).invoke();
//        if (checkUser.isMyResult()) {
//            return BaseResult.error(checkUser.getMsg());
//        }
//        record = checkUser.getUser();
//        if (record.getId() == null) {
//            record.setCreateTime(DateTime.now());
//            // TODO ShiroUtil ?? record.setCreator(ShiroUtil.getUserName());
//        } else {
//            record.setUpdateTime(DateTime.now());
//        }
//        int result = userService.save(record);
//        if (result == 0) {
//            return I18nUtils.getBaseResultError("operate.fail.check.param");
//        }
//        return I18nUtils.getBaseResultOk("operate.success");
//    }
//
//    /**
//     * 删除用户
//     * @param records
//     * @return
//     */
//    @PostMapping(value="/delete")
//    public BaseResult delete(@RequestBody List<User> records) {
//        if (records.size()>0) {
//            userService.delete(records);
//            return I18nUtils.getBaseResultOkBySoleParam("delete.number.data", records.size());
//        } else {
//            return I18nUtils.getBaseResultError("delete.no.data.check.param");
//        }
//    }
//
//    /**
//     * 分页查询
//     * @param pageRequest
//     * @return
//     */
//    @PostMapping(value="/findPage")
//    public BaseResult findPage(@RequestBody PageRequest pageRequest) {
//        PageResult result = userService.findPage(pageRequest);
//        return BaseResult.ok(result);
//    }
//
//    /**
//     * 根据主键查询
//     * @param id
//     * @return
//     */
//    @GetMapping(value="/findById")
//    public BaseResult findById(@RequestParam long id) {
//        return BaseResult.ok(userService.findById(id));
//    }
//
//    /**
//     * 条件查询用户信息列表
//     * @param map 查询条件集合
//     * @return
//     */
//    @PostMapping(value="/findList")
//    public BaseResult findList(@RequestBody Map map) {
//        List<User> list = userService.findList(map);
//        return BaseResult.ok(list);
//    }
//
//    /**
//     * 启用禁用（原值为禁用则改为启用，反之亦然）
//     * @param id
//     * @return
//     */
//    @GetMapping(value="/disable")
//    public BaseResult disable(@RequestParam long id) {
//        User user = userService.findById(id);
//        if (BeanUtil.isEmpty(user)) {
//            return I18nUtils.getBaseResultError("operate.fail.check.param");
//        }
//        if (null != user.getStatus()) {
//            user.setStatus(user.getStatus() == ConfigConsts.DISABLE ? ConfigConsts.ENABLE : ConfigConsts.DISABLE);
//        } else {
//            user.setStatus(ConfigConsts.DISABLE);
//        }
//        int result = userService.save(user);
//        if (result == 0) {
//            return I18nUtils.getBaseResultError("operate.fail.check.param");
//        }
//        return I18nUtils.getBaseResultOk("operate.success");
//    }
//
//    private class CheckUser {
//        private boolean myResult;
//        private User record;
//        private String msg;
//
//        public CheckUser(User record) {
//            this.record = record;
//        }
//
//        public boolean isMyResult() {
//            return myResult;
//        }
//
//        public User getUser() {
//            return record;
//        }
//
//        public String getMsg() {
//            return msg;
//        }
//
//        public CheckUser invoke() {
//            if (record == null) {
//                msg = I18nUtils.getMessage("operate.fail.check.param");
//                myResult = true;
//                return this;
//            }
//            myResult = false;
//            return this;
//        }
//    }
//
//}
//
