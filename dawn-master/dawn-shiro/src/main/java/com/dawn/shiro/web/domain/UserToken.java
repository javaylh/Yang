package com.dawn.shiro.web.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * ---------------------------
 * 用户Token (UserToken)         
 * ---------------------------
 * @author： ylh
 * @date： 2020-08-20 09:50:00
 * ---------------------------
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserToken implements Serializable {

    private static final long serialVersionUID = 1L;

	/** 用户id */
	private Long userId;

	/*** 用户名*/
	private String userName;

	/** 用户角色id */
	private String roleId;

	/** token */
	private String token;

    /** 过期时间 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date expireTime;

    /** 创建时间 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;

    /** 更新时间 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date lastUpdateTime;

}