package com.zhu.study.seckill.result;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 返回对象
 * @author zhusl 
 * @date 2020年7月24日  上午9:44:02
 *
 */
@Data
@AllArgsConstructor
public class JsonResult<T> {
	private Integer code;
	private String msg;
	private T data;
	
	public JsonResult(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
