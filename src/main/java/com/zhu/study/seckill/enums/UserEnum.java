package com.zhu.study.seckill.enums;

import lombok.Getter;

/**
 * @author zhusl 
 * @date 2020年7月24日  上午9:49:32
 *
 */
@Getter
public enum UserEnum {
	
	SUCCESS(200,"success"),
    FAIL(201,"fail"),
    LOGIN_FAIL(202,"登录失败，用户名或密码错误"),
    NULL_TOKEN(203,"无效token，请重新登录"),
    SYS_ERROR(500,"系统异常")
    ;
    private Integer code;
    private String message;
    UserEnum(Integer code,String message) {
        this.code = code;
        this.message = message;
    }
}
