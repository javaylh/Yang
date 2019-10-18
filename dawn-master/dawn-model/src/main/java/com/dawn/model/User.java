package com.dawn.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * ---------------------------
 * 用户信息 (User)
 * ---------------------------
 * @author ylh
 * @date 2019-10-18 09:30:00
 * ---------------------------
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

	/** ID号 */
	private Long id;

	/** 用户名 */
	private String userName;

	/** 密码 */
	private String password;

	/** 密码加密盐 */
	private String salt;

	/** 用户真实姓名或昵称 */
	private String trueName;

	/** 角色Ids */
	private String roleId;

	/** 角色名称 */
	private String roleName;

	/** 权限 */
	private String auth;

	/** 权限名称 */
	private String authName;

	/** 状态 0：禁用 1：启用 */
	private Integer status;

	/** 排序，越大越靠前 */
	private Integer sequence;

	/** 用户类型：1普通用户，100：管理员 */
	private Integer type;

	/** 邮箱 */
	private String email;

	/** 用户头像 */
	private String avatarUrl;

	/** 0：账号登陆，1：github登陆 */
	private Integer loginType;

	/** 第三方唯一ID */
	private String thirdlyId;

    /** 创建时间 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;

	/** 创建者 */
	private Long creatorId;

    /** 修改时间 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;

	/** 修改者 */
	private Long modifierId;

}