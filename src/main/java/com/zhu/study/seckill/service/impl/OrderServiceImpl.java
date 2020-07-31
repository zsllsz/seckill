package com.zhu.study.seckill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhu.study.seckill.entity.Order;
import com.zhu.study.seckill.repository.OderRepository;
import com.zhu.study.seckill.service.OrderService;

/**
 * @author zhusl 
 * @date 2020年7月24日  下午5:07:41
 *
 */
@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OderRepository orderRepository;

	@Transactional
	@Override
	public void createOrder(Order order) {
		orderRepository.save(order);
	}

}
