package com.zhu.study.seckill.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhu.study.seckill.entity.Order;
import com.zhu.study.seckill.entity.SeckillGoods;
import com.zhu.study.seckill.redis.RedisUtil;
import com.zhu.study.seckill.repository.SeckillRepository;
import com.zhu.study.seckill.service.OrderService;
import com.zhu.study.seckill.service.SeckillService;

/**
 * @author zhusl 
 * @date 2020年7月24日  下午3:52:40
 *
 */
@Service
public class SeckillServiceImpl implements SeckillService {

	@Autowired
	private SeckillRepository seckillRepository;
	@Autowired
	private OrderService orderService;
	
	@Override
	public List<SeckillGoods> getAllSeckillGoods() {
		return seckillRepository.findAll();
	}

	@Override
	public SeckillGoods findById(Integer sgId) {
		return seckillRepository.findById(sgId).get();
	}

	@Transactional
	@Override
	public void seckill(Integer userId, Integer sgId) {
		
		// 1. 扣库存
		if (!reduceStock(sgId)) {
			return;
		}
		
		// 2. 生成订单
		Order order = new Order();
		order.setOUserId(userId);
		order.setOGoodsId(sgId);
		order.setOCount(1);
		// 其他属性等秒杀成功支付的时候再去更新，addressId不能为空，先设置一个固定值
		order.setOAddressId(1);
		orderService.createOrder(order);
		
		// 3. 将userId和sgId作为key放入redis中，表示该用户已经秒杀过该商品
		RedisUtil.set(String.valueOf(userId) + sgId, true);
	}

	@Transactional
	@Override
	public boolean reduceStock(Integer sgId) {
		SeckillGoods goods = findById(sgId);
		if (goods.getSgSeckillNum() <= 0) {
			return false;
		}
		Integer stock = goods.getSgSeckillNum() - 1;
		goods.setSgSeckillNum(stock);
		seckillRepository.save(goods);
		return true;
	}

}
