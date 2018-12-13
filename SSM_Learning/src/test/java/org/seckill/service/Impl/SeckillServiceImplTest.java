package org.seckill.service.Impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.SecKill;
import org.seckill.exception.RepeatKillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author yangxin
 * @time 2018/12/7  13:57
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/springDao-config.xml",
        "classpath:spring/springService-config.xml"})
public class SeckillServiceImplTest {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() {
        List<SecKill> secKill=seckillService.getSeckillList();
        logger.info("list={}",secKill);
    }

    @Test
    public void getById() {
        long id=1000;
        SecKill secKill=seckillService.getById(id);
        logger.info("secKill={}",secKill);
    }

    @Test
    public void exportSeckillUrl() {
        long id=1000;
        Exposer exposer=seckillService.exportSeckillUrl(id);
        logger.info("exposer={}",exposer);
        //Exposer{exposed=true, md5='16050dc4a64b9b243d541d0ddf3b935f', seckillId=1000, now=0, start=0, end=0}
    }

    @Test
    public void executeSeckill() {
        long id=1000;
        long phone=18555663589L;
        String salt="16050dc4a64b9b243d541d0ddf3b935f";
        try{
            SeckillExecution seckillExecution=seckillService.executeSeckill(id,phone,salt);
            logger.info("seckillExecution={}"+seckillExecution);
        }catch (RepeatKillException e){
            logger.error(e.getMessage(),e);
        }
    }
    @Test
    public void executeProcedure(){
        long id=1001;
        long phone=12355669988L;
        Exposer exposer=seckillService.exportSeckillUrl(id);
        if(exposer.isExposed()){
            String md5=exposer.getMd5();
            SeckillExecution execution=seckillService.executeSeckill2(id,phone,md5);
            logger.info(execution.getStateInfo());
        }

    }
}