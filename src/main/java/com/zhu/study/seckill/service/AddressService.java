package com.zhu.study.seckill.service;

import com.zhu.study.seckill.entity.Address;

/**
 * @author zhusl 
 * @date 2020年7月24日  下午5:16:09
 *
 */
public interface AddressService {
	
	public Address findByUserId(Integer userId);

}
