package com.dawn.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * ---------------------------
 * 错误信息 (Error)
 * ---------------------------
 * @author ylh
 * @date 2019-10-18 09:30:00
 * ---------------------------
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error implements Serializable {

    private static final long serialVersionUID = 1L;

	/** 主键 */
	private Long id;

	/** 错误码编码 */
	private String errorCode;

	/** 错误码描述 */
	private String errorMsg;

	/** 模块ID */
	private Long moduleId;

    /** 创建时间 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;

	/** 状态 */
	private Integer status;

	/** 排序，越大越靠前 */
	private Integer sequence;

}