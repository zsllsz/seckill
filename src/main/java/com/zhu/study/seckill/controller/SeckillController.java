package com.zhu.study.seckill.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.google.common.util.concurrent.RateLimiter;
import com.zhu.study.seckill.entity.SeckillGoods;
import com.zhu.study.seckill.entity.User;
import com.zhu.study.seckill.enums.SeckillGoodsEnum;
import com.zhu.study.seckill.mq.RabbitMqSender;
import com.zhu.study.seckill.redis.RedisUtil;
import com.zhu.study.seckill.result.JsonResult;
import com.zhu.study.seckill.service.SeckillService;
import com.zhu.study.seckill.service.UserService;

/**
 * @author zhusl 
 * @date 2020年7月24日  上午10:01:58
 *
 */
@RestController
@RequestMapping("/seckill")
public class SeckillController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private SeckillService seckillService;
	@Autowired
	private RabbitMqSender mqSender;
	
	// 用来标记商品是否已经加入到redis中的key
	private static final String ISINREDIS = "isInRedis";
	
	// 用goodsId作为key，标记该商品是否已经卖完
	private Map<Integer, Boolean> seckillOver = new HashMap<Integer, Boolean>();
	
	// 用RateLimiter做限流，create(10)，可以理解为QPS阈值为10
	private RateLimiter rateLimiter = RateLimiter.create(10);
	
	@PostMapping("/{sgId}")
	public JsonResult<?> seckillGoods(@PathVariable("sgId") Integer sgId, HttpServletRequest httpServletRequest){
		
		// 1. 如果QPS阈值超过10，即1秒钟内没有拿到令牌，就返回“人太多了，挤不进去”的提示
		if (!rateLimiter.tryAcquire(1, TimeUnit.SECONDS)) {
			return new JsonResult<>(SeckillGoodsEnum.TRY_AGAIN.getCode(), SeckillGoodsEnum.TRY_AGAIN.getMessage());
		}
		
		// 2. 检查用户是否登录（用户登录后，访问每个接口都应该在请求头带上token，根据token再去拿user）
		String token = httpServletRequest.getHeader("token");
		String userId = JWT.decode(token).getAudience().get(0);
		User user = userService.findUserById(Integer.valueOf(userId));
		if (user == null) {
			return new JsonResult<>(SeckillGoodsEnum.INVALID_TOKEN.getCode(), SeckillGoodsEnum.INVALID_TOKEN.getMessage());
		}
		
		// 3. 如果商品已经秒杀完了，就不执行下面的逻辑，直接返回商品已秒杀完的提示
		if (!seckillOver.isEmpty() && seckillOver.get(sgId)) {
			return new JsonResult<>(SeckillGoodsEnum.SECKILL_OVER.getCode(), SeckillGoodsEnum.SECKILL_OVER.getMessage());
		}
		
		// 4. 将所有参加秒杀的商品信息加入到redis中
		if (!RedisUtil.isExist(ISINREDIS)) {
			List<SeckillGoods> goods = seckillService.getAllSeckillGoods();
			for (SeckillGoods seckillGoods : goods) {
				RedisUtil.set(String.valueOf(seckillGoods.getSgId()), seckillGoods.getSgSeckillNum());
				seckillOver.put(seckillGoods.getSgId(), false);
			}
			RedisUtil.set(ISINREDIS, true);
		}
		
		// 5. 先自减，预扣库存，判断预扣后库存是否小于0，如果是，表示秒杀完了
		Long stock = RedisUtil.decr(String.valueOf(sgId));
		if (stock < 0) {
			// 标记该商品已经秒杀完
			seckillOver.put(sgId, true);
			return new JsonResult<>(SeckillGoodsEnum.SECKILL_OVER.getCode(), SeckillGoodsEnum.SECKILL_OVER.getMessage());
		}
		
		// 6. 判断是否重复秒杀（成功秒杀并创建订单后，会将userId和goodsId作为key放到redis中）
		if (RedisUtil.isExist(userId + sgId)) {
			return new JsonResult<>(SeckillGoodsEnum.REPEAT_SECKILL.getCode(), SeckillGoodsEnum.REPEAT_SECKILL.getMessage());
		}
		
		// 7. 以上校验都通过了，就将当前请求加入到MQ中，然后返回“排队中”的提示
		String msg = userId + "," + sgId;
		mqSender.send(msg);
		return new JsonResult<>(SeckillGoodsEnum.LINE_UP.getCode(), SeckillGoodsEnum.LINE_UP.getMessage());
	}

}
