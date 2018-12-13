package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKill;

public interface SuccessKillDao {
	
	/*
	 * 插入购买明细、可过滤重复
	 * 返回值返回插入的数据行数。返回0表示插入失败。
	 */
	public int insertSuccessKilled(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
	
	/*
	 * 根据id查询SuccessKilled并携带秒杀产品对象实例
	 */
	public SuccessKill queryByIdWithSecKill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
	
	

}
