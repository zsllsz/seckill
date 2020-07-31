package com.zhu.study.seckill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.zhu.study.seckill.entity.Address;
import com.zhu.study.seckill.repository.AddressRepository;
import com.zhu.study.seckill.service.AddressService;

/**
 * @author zhusl 
 * @date 2020年7月24日  下午5:17:16
 *
 */
@Service
public class AddressServiceImpl implements AddressService{
	
	@Autowired
	private AddressRepository addressRepository;

	@Override
	public Address findByUserId(Integer userId) {
		Address address = new Address();
		Example<Address> example = Example.of(address);
		return addressRepository.findOne(example).get();
	}

}
