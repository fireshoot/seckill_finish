package org.seckill.dao;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/springDao-config.xml"})
public class SuccessKillDaoTest {
	
	@Resource
	private SuccessKillDao successKillDao;

	@Test
	public void testInsertSuccessKilled() {
		
		long seckillId=1000L;
		long userPhone=18162793488L;
		int count=successKillDao.insertSuccessKilled(seckillId, userPhone);
		System.out.println("Insert Number:"+count);
	}

	@Test
	public void testQueryByIdWithSuccessKill() {
		//System.out.println("dada");
		long seckillId=1000L;
		long userPhone=18162793488L;
		
		SuccessKill successKill=successKillDao.queryByIdWithSecKill(seckillId, userPhone);
		System.out.println(successKill);
		System.out.println(successKill.getSecKill());

	}

}
