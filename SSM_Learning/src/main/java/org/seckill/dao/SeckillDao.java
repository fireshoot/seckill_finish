package org.seckill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SecKill;

public interface SeckillDao {
	
	/*
	 * 减库存函数
	 * 如果返回的数字>1，表示影响的行数。，返回0更新语句可能没有成功
	 */

	public int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime")Date killTime);


	/*
	 * 查询接口
	 */

	public SecKill queryById(long seckillId);

	/*
	 * 根据偏移量查询秒杀商品列表
	 * 这里为什么要使用@Param注解;因为java本身没有保存形参的能力，将一个函数的形参都表示成args0，args1......
	 * 但是我们在SeckillDao.xml的Sql语句中，我们有一个 limit #{offset},#{limit};里面需要offset和limit，所有
	 * 这里的注解就是解决这个问题。
	 */
	public List<SecKill> queryAll(@Param("offset")int offset,@Param("limit")int limit);


	/*
	* 使用存储过程执行秒杀，
	* */
	void killByProcedure(Map<String,Object> map);


}
