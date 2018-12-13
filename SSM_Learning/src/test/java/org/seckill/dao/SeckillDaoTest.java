package org.seckill.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SecKill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
 * 配置spring和Junit整合，Junit启动时加载springIOc
 * SPring-test，jUnit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit Spring配置文件
@ContextConfiguration({"classpath:spring/springDao-config.xml"})
public class SeckillDaoTest {
	
	//注入Dao依赖
	
	@Resource
	private SeckillDao seckillDao; 
	
	@Test
	public void testQueryById() throws Exception {
		long id=1000;
		
		SecKill secKill=seckillDao.queryById(id);
		System.out.println(secKill.getName());
		System.out.println(secKill);
		//fail("Not yet implemented");
	}
	
	@Test
	public void testQueryAll() {
		List<SecKill> list=seckillDao.queryAll(0, 100);
		for(SecKill secKill:list)
			System.out.println(secKill);
	}
	
	/*
	 *
	 */
	
	@Test
	public void testReduceNumber() {
		Date date =new Date();
		
		int count=seckillDao.reduceNumber(1000, date);
		
		System.out.println("update:  "+count);
	}




}
