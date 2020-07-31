package com.zhu.study.seckill.service;

import com.zhu.study.seckill.entity.User;

/**
 * @author zhusl 
 * @date 2020年7月23日  下午7:07:49
 *
 */
public interface UserService {
	
	public User findUserById(Integer id);
	
	public User login(User user);

}
