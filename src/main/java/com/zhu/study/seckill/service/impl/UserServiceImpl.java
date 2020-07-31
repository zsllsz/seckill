package com.zhu.study.seckill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.zhu.study.seckill.entity.User;
import com.zhu.study.seckill.repository.UserRepository;
import com.zhu.study.seckill.service.UserService;

/**
 * @author zhusl 
 * @date 2020年7月24日  上午9:34:12
 *
 */
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findUserById(Integer id) {
	    return userRepository.findById(id).get();
	}

	@Override
	public User login(User user) {
		Example<User> example = Example.of(user);
		return userRepository.findOne(example).get();
	}
}
