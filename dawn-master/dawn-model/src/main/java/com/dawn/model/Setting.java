package com.dawn.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * ---------------------------
 * 设置 (Setting)
 * ---------------------------
 * @author ylh
 * @date 2019-10-18 09:30:00
 * ---------------------------
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Setting implements Serializable {

    private static final long serialVersionUID = 1L;

	/** ID号 */
	private Long id;

	/** key值 */
	private String mkey;

	/** value值 */
	private String value;

	/** 备注 */
	private String remark;

	/** 状态 0：禁用 1：启用 */
	private Integer status;

	/** 设置类型（IMAGE,LINK,TEXT） */
	private String type;

	/** 1：可删除，0：不可删除 */
	private Integer canDelete;

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