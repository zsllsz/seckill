package com.zhu.study.seckill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhu.study.seckill.entity.Order;

/**
 * @author zhusl 
 * @date 2020年7月24日  下午5:02:21
 *
 */
public interface OderRepository extends JpaRepository<Order, Integer>{

}
