package com.zhu.study.seckill.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 用户实体
 * @author zhusl 
 * @date 2020年7月23日  上午9:39:26
 *
 */
@Data
@Entity
@Table(name = "tb_user")
public class User {

	// 主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer uId;
	// 用户名
	private String uName;
	// 密码
	private String uPassword;
}
