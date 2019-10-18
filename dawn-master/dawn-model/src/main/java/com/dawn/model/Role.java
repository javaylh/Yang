package com.dawn.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * ---------------------------
 * 角色信息 (Role)
 * ---------------------------
 * @author ylh
 * @date 2019-10-18 09:30:00
 * ---------------------------
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

	/** 角色ID */
	private Long id;

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