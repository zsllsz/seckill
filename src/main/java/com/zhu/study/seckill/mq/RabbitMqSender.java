package com.zhu.study.seckill.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhusl 
 * @date 2020年7月21日  下午5:11:02
 *
 */
@Component
@Slf4j
public class RabbitMqSender {
	
	@Value("${rabbitmq.queue.name}")
	private String queueName;
	
//	@Value("${rabbitmq.exchange.name}")
//	private String exchangeName;
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	public void send(String msg) {
		log.info("【RabbitMqSender】send：消息：" + msg + " 发送到队列 " + queueName + " 中");
		amqpTemplate.convertAndSend(queueName, msg.getBytes());
	}
}
