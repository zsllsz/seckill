package com.zhu.study.seckill.jwt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhusl 
 * @date 2020年7月24日  上午10:50:11
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SkipToken {
	 boolean required() default true;
}
