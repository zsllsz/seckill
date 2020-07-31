package com.zhu.study.seckill.service;

import com.zhu.study.seckill.entity.Order;

/**
 * @author zhusl 
 * @date 2020年7月24日  下午5:05:38
 *
 */
public interface OrderService {
	
	/**
	 * 创建订单
	 * @param userId
	 * @param sgId
	 */
	public void createOrder(Order order);

}
