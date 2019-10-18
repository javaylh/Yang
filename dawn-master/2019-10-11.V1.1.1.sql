/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50512
 Source Host           : 127.0.0.1:3306
 Source Schema         : dawn

 Target Server Type    : MySQL
 Target Server Version : 50512
 File Encoding         : 65001

 Date: 11/10/2019 15:20:00
*/

-- ----------------------------
-- Table structure for error
-- ----------------------------
DROP TABLE IF EXISTS `error`;
CREATE TABLE `error`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `error_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '错误码编码',
  `error_msg` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '错误码描述',
  `module_id` bigint(20) NOT NULL COMMENT '模块ID',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态',
  `sequence` int(11) NOT NULL DEFAULT 0 COMMENT '排序，越大越靠前',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_mod_seq_time`(`module_id`, `sequence`, `create_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '错误信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` bigint(20) NOT NULL COMMENT 'ID号',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态 0：禁用 1：启用',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `sequence` int(11) NOT NULL DEFAULT 0 COMMENT '排序，越大越靠前',
  `model_class` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模块类型',
  `model_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模块名称',
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作类型（ADD,UPDATE,DELTET）',
  `update_by` bigint(20) NOT NULL COMMENT '操作人ID',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '备注（修改：xxxx）',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `identy` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据唯一主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '日志信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint(20) NOT NULL COMMENT 'ID号',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `menu_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单链接',
  `role_ids` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色可见集合  （ID之间以逗号分隔）',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父级ID',
  `icon_remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `type` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '前端菜单、后台菜单',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态 0：禁用 1：启用',
  `sequence` int(11) NOT NULL DEFAULT 0 COMMENT '排序，越大越靠前',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` bigint(20) NOT NULL COMMENT '创建者',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `modifier_id` bigint(20) NULL DEFAULT NULL COMMENT '修改者',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_parentId`(`parent_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(20) NOT NULL COMMENT '角色ID',
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `auth` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限',
  `auth_name` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '权限名称',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态 0：禁用 1：启用',
  `sequence` int(11) NOT NULL DEFAULT 0 COMMENT '排序，越大越靠前',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` bigint(20) NOT NULL COMMENT '创建者',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `modifier_id` bigint(20) NULL DEFAULT NULL COMMENT '修改者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for setting
-- ----------------------------
DROP TABLE IF EXISTS `setting`;
CREATE TABLE `setting`  (
  `id` bigint(20) NOT NULL COMMENT 'ID号',
  `mkey` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'key值',
  `value` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'value值',
  `remark` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态 0：禁用 1：启用',
  `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'TEXT' COMMENT '设置类型（IMAGE,LINK,TEXT）',
  `can_delete` tinyint(4) NOT NULL DEFAULT 0 COMMENT '1：可删除，0：不可删除',
  `sequence` int(11) NOT NULL DEFAULT 0 COMMENT '排序，越大越靠前',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` bigint(20) NOT NULL COMMENT '创建者',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `modifier_id` bigint(20) NULL DEFAULT NULL COMMENT '修改者',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `key`(`mkey`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设置' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL COMMENT 'ID号',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `true_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户真实姓名或昵称',
  `role_id` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色Ids',
  `role_name` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `auth` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '权限',
  `auth_name` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '权限名称',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态 0：禁用 1：启用',
  `sequence` int(11) NOT NULL DEFAULT 0 COMMENT '排序，越大越靠前',
  `type` tinyint(4) NOT NULL DEFAULT 100 COMMENT '用户类型：1普通用户，100：管理员',
  `email` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `avatar_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户头像',
  `login_type` int(11) NOT NULL DEFAULT 0 COMMENT '0：账号登陆，1：github登陆',
  `thirdly_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方唯一ID',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` bigint(20) NOT NULL COMMENT '创建者',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `modifier_id` bigint(20) NULL DEFAULT NULL COMMENT '修改者',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `loginType_userName`(`user_name`, `login_type`) USING BTREE,
  UNIQUE INDEX `loginType_Email`(`email`, `login_type`) USING BTREE,
  INDEX `index_thirdlyId`(`thirdly_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '21232f297a57a5a743894a0e4a801fc3', '超级管理员', 'super,', '超级管理员,', '', '', 1, 0, 100, NULL, '', 0, NULL, '2019-10-11 15:20:00', 0, NULL, NULL);

-- ----------------------------
-- New field of user  ylh 2019-10-18
-- ----------------------------
ALTER TABLE `dawn`.`user`
ADD COLUMN `salt` varchar(50) NOT NULL COMMENT '密码加密盐' AFTER `password`;