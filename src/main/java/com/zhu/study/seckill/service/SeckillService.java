package com.zhu.study.seckill.service;

import java.util.List;

import com.zhu.study.seckill.entity.SeckillGoods;

/**
 * @author zhusl 
 * @date 2020年7月24日  下午3:48:38
 *
 */
public interface SeckillService {
	
	/**
	 * 获取所有秒杀商品
	 * @return
	 */
	public List<SeckillGoods> getAllSeckillGoods();
	
	/**
	 * 根据商品id查询商品
	 * @param sgId
	 * @return
	 */
	public SeckillGoods findById(Integer sgId);
	
	/**
	 * 秒杀
	 * @param userId
	 * @param sgId
	 */
	public void seckill(Integer userId, Integer sgId);
	
	/**
	 * 扣库存，每次库存减1
	 * @param sgId
	 * @return
	 */
	public boolean reduceStock(Integer sgId);
	
}
