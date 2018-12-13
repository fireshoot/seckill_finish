package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SecKill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author yangxin
 * @time 2018/12/12  9:34
 */
@RunWith(SpringJUnit4ClassRunner.class)
//∏ÊÀﬂJunit Spring≈‰÷√Œƒº˛
@ContextConfiguration({"classpath:spring/springDao-config.xml"})
public class ResdisDaoTest {
    private long id=1003;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao secKillDao;

    @Test
    public void Seckill() {
        SecKill seckill=redisDao.getSeckill(id);
        if(seckill==null){
            seckill=secKillDao.queryById(id);
            if(seckill!=null) {
                String string = redisDao.setSeckill(seckill);
                System.out.println(string);
                seckill = redisDao.getSeckill(id);
                System.out.println(seckill);
            }
        }
    }

}