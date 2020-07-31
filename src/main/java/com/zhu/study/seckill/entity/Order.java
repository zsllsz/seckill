package com.zhu.study.seckill.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 订单实体
 * @author zhusl 
 * @date 2020年7月22日  下午6:40:08
 *
 */
@Data
@Entity
@Table(name = "tb_order")
public class Order {
	// 主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer oId;
	// 商品id
	private Integer oGoodsId;
	// 用户id
	private Integer oUserId;
	// 收货地址id
	private Integer oAddressId;
	// 商品数量
	private Integer oCount;
	// 订单总价
	private BigDecimal oAmount;
	// 订单状态 0:待支付 1：已支付 2：已取消
	private Integer oStatus;
	// 订单创建时间
	private Date oCreateTime;
	// 订单支付时间
	private Date oPayTime;

}
