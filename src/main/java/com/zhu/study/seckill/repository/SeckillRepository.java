package com.zhu.study.seckill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zhu.study.seckill.entity.SeckillGoods;

/**
 * @author zhusl 
 * @date 2020年7月24日  下午3:47:48
 *
 */
public interface SeckillRepository extends JpaRepository<SeckillGoods, Integer>{

}
