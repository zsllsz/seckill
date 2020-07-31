create database seckill;
use seckill;
create table `tb_user`(
    `u_id` integer not null auto_increment comment '主键，自增',
    `u_name` varchar(20) not null comment '用户名',
    `u_password` varchar(30) not null comment '用户密码',
    primary key (`u_id`)
) comment = '用户表' engine = InnoDB default charset = utf8;


create table `tb_order`(
    `o_id` integer not null auto_increment comment '主键，自增',
    `o_goods_id` integer not null comment '商品id',
    `o_user_id` integer not null comment '用户id',
    `o_address_id` integer not null comment '收货地址',
    `o_count` integer comment '商品数量',
    `o_amount` numeric comment '订单总价',
    `o_status` integer comment '订单状态 0:待支付，1:已支付，2:已取消',
    `o_create_time` timestamp default current_timestamp comment '创建时间',
    `o_pay_time` timestamp comment '订单支付时间',
    primary key (`o_id`)
) comment = '订单表' engine = InnoDB default charset = utf8;

create table `tb_address`(
    `a_id` integer not null auto_increment comment '主键，自增',
    `a_user_id` integer not null comment '用户id',
    `a_country` varchar(30) comment '国家',
    `a_province` varchar(30) comment '省份',
    `a_city` varchar(30) comment '市',
    `a_state` varchar(30) comment '区/镇',
    `a_detail_addr` varchar(100) comment '详细地址',
    primary key (`a_id`)
) comment = '地址表' engine = InnoDB default charset = utf8;

insert into tb_address(a_user_id, a_country, a_province, a_city, a_state, a_detail_addr) values (1,'中国','广东省','深圳市','南山区','广东深圳南山必胜客');

create table `tb_seckill_goods`(
    `sg_id` integer not null auto_increment comment '主键，自增',
    `sg_seckill_goods_name` varchar(200) not null comment '商品名称',
    `sg_seckill_price` numeric comment '秒杀价',
    `sg_seckill_num` integer comment '秒杀数量',
    primary key (`sg_id`)
) comment = '秒杀表' engine = InnoDB default charset = utf8;

insert into tb_seckill_goods(sg_seckill_goods_name, sg_seckill_price, sg_seckill_num) values ('小米10','1999','1000');

select * from tb_seckill_goods;
select * from tb_order;
select count(*) from tb_user;

# 写个存储过程，创建1000个用户
DELIMITER ;;
CREATE PROCEDURE batchAddUser()
BEGIN
    DECLARE i int DEFAULT 1;
    WHILE i < 1000
        DO
            insert into tb_user(u_name, u_password) VALUES (concat(i,'test'), concat(i,'test'));
            SET i = i + 1;
        END WHILE;
    commit;
END;;
CALL batchAddUser();