package com.zhu.study.seckill.mq;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zhu.study.seckill.entity.SeckillGoods;
import com.zhu.study.seckill.redis.RedisUtil;
import com.zhu.study.seckill.service.SeckillService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhusl 
 * @date 2020年7月21日  下午5:11:40
 *
 */
@Component
@Slf4j
public class RabbitMqReceiver {
	
	@Autowired
	private SeckillService seckillService;
	
	@Value("${rabbitmq.queue.name}")
	private String queueName;
	
	/**
	 * 注意这个方法不能有返回值
	 * @param message
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue("${rabbitmq.queue.name}"),
			exchange = @Exchange("${rabbitmq.exchange.name}")))
	public void receive(byte[] message) {
		String msg = new String(message);
		log.info("【RabbitMqReceiver】receive：成功从队列 " + queueName + " 中消费消息：" + msg);
		// 创建秒杀订单
		createSeckillOrder(msg);
	}

	/**
	 * 创建秒杀订单
	 * @param msg
	 */
	private void createSeckillOrder(String msg) {
		String userId = msg.split(",")[0];
		String sgId = msg.split(",")[1];
		
		// 1. 根据sgId查询该商品库存是否大于0
		SeckillGoods seckillGoods = seckillService.findById(Integer.valueOf(sgId));
		if (seckillGoods.getSgSeckillNum() <= 0) {
			return;
		}
		
		// 2. 再次判断是否重复秒杀
		if (RedisUtil.isExist(userId + sgId)) {
			return;
		}
		
		// 3. 扣库存，创建订单
		seckillService.seckill(Integer.valueOf(userId), Integer.valueOf(sgId));
	}

}
