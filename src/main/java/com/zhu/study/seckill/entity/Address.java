package com.zhu.study.seckill.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 地址实体
 * @author zhusl 
 * @date 2020年7月23日  上午10:14:28
 *
 */
@Data
@Entity
@Table(name = "tb_address")
public class Address {
	
	// 主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer aId;
	// 用户id
	private Integer aUserId;
	// 国家
	private String aCountry;
	// 省
	private String aProvince;
	// 市
	private String aCity;
	// 区/镇
	private String aState;
	// 详细地址
	private String aDetailAddr;
}
