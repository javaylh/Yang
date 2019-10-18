package com.dawn.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * ---------------------------
 * 菜单信息 (Menu)
 * ---------------------------
 * @author ylh
 * @date 2019-10-18 09:30:00
 * ---------------------------
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

	/** ID号 */
	private Long id;

	/** 菜单名称 */
	private String menuName;

	/** 菜单链接 */
	private String menuUrl;

	/** 角色可见集合  （ID之间以逗号分隔） */
	private String roleIds;

	/** 父级ID */
	private Long parentId;

	/** 图标 */
	private String iconRemark;

	/** 前端菜单、后台菜单 */
	private String type;

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