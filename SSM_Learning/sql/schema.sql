 --数据库初始化脚本

--创建数据库

CREATE database seckill;

--使用数据库

use seckill;

--创建秒杀库存表

create table seckill(

	`seckill_id` bigint NOT NULL AUTO_INCREMENT  COMMENT '商品库存ID',
	`name` varchar(120) NOT NULL COMMENT '商品的名称',
	`number` int NOT NULL COMMENT '库存数量',
	`start_time` timestamp NOT NULL COMMENT '秒杀开始时间',
	`end_time` timestamp NOT NULL COMMENT '秒杀结束时间',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY(seckill_id),
	key idx_start_time(start_time),
	key idx_end_time(end_time),
	key idx_create_time(create_time)	
)engine=InnoDB auto_INCREMENT=1000 DEFAULT CHARSET=utf8 comment='秒杀库存表';

---初始化数据
insert into seckill(name,number,start_time,end_time)
values
('1000元秒杀ipone12X',100,'2018-11-11 00:00:00','2018-11-12 00:00:00'),
('100元秒杀ipad',100,'2018-11-11 00:00:00','2018-11-12 00:00:00'),
('1300元秒杀小米',100,'2018-11-11 00:00:00','2018-11-12 00:00:00'),
('700元秒杀华为mate20',100,'2018-11-11 00:00:00','2018-11-12 00:00:00'),
('1000元秒杀oppo',100,'2018-11-11 00:00:00','2018-11-12 00:00:00'),
('600元秒杀女比亚',100,'2018-11-11 00:00:00','2018-11-12 00:00:00'),
('1000元秒杀iponeX',100,'2018-11-11 00:00:00','2018-11-12 00:00:00');

---秒杀成功明细表
---用户登录认证相关信息
create table success_killed(
	`seckill_id` bigint not null comment '秒杀商品id',
	`user_phone` bigint not null comment '用户手机号码',
	`state` tinyint not null default -1 comment '状态标识，-1：无效，0：成功，1：已付款，2：已发货..',
	`create_time` timestamp not null comment '创建时间',
	primary key(seckill_id,user_phone),/*联合主键*/
	key idx_crate_time(create_time)
)engine=InnoDB DEFAULT CHARSET=utf8 comment='秒杀成功明细表';

---连接数据库控制台

mysql -uroot -p

