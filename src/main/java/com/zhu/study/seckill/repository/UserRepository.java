package com.zhu.study.seckill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhu.study.seckill.entity.User;

/**
 * @author zhusl 
 * @date 2020年7月23日  下午7:00:38
 *
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}
