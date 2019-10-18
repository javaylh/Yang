package com.dawn.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * ---------------------------
 * 日志信息 (Log)
 * ---------------------------
 * @author ylh
 * @date 2019-10-18 09:30:00
 * ---------------------------
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

	/** ID号 */
	private Long id;

	/** 状态 0：禁用 1：启用 */
	private Integer status;

    /** 创建时间 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;

	/** 排序，越大越靠前 */
	private Integer sequence;

	/** 模块类型 */
	private String modelClass;

	/** 模块名称 */
	private String modelName;

	/** 操作类型（ADD,UPDATE,DELTET） */
	private String type;

	/** 操作人ID */
	private Long updateBy;

	/** 备注（修改：xxxx） */
	private String remark;

	/** 内容 */
	private String content;

	/** 数据唯一主键 */
	private String identy;

}