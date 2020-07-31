package com.zhu.study.seckill.enums;

import lombok.Getter;

/**
 * @author zhusl 
 * @date 2020年7月24日  上午10:17:59
 *
 */
@Getter
public enum SeckillGoodsEnum {

	TRY_AGAIN(201,"人太多了，挤不进去，请稍后重试"),
	INVALID_TOKEN(202,"无效的token，请重新登录"),
	SECKILL_OVER(203,"来迟了，商品已秒杀完"),
	REPEAT_SECKILL(204,"请勿重复秒杀"),
	LINE_UP(200,"排队中")
    ;
    private Integer code;
    private String message;
    SeckillGoodsEnum(Integer code,String message) {
        this.code = code;
        this.message = message;
    }
}
