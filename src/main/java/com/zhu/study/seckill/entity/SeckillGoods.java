package com.zhu.study.seckill.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 秒杀的商品
 * @author zhusl 
 * @date 2020年7月23日  下午2:15:52
 *
 */
@Data
@Entity
@Table(name = "tb_seckill_goods")
public class SeckillGoods {
	
	// 主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sgId;
	// 秒杀的商品名称
	private String sgSeckillGoodsName;
	// 秒杀的商品价格
	private String sgSeckillPrice;
	// 秒杀数量
	private Integer sgSeckillNum;
}
