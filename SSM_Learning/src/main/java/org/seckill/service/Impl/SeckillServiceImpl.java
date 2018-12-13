package org.seckill.service.Impl;

import org.apache.commons.collections.MapUtils;
import org.seckill.dao.RedisDao;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKillDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.SecKill;
import org.seckill.entity.SuccessKill;
import org.seckill.enums.SeckillStateEnums;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangxin
 * @time 2018/12/7  9:38
 */

@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SuccessKillDao successKillDao;

    //MD5的盐。
    private final String salt="dsaiofpjsfs";

    @Override
    public List<SecKill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    @Override
    public SecKill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        SecKill secKill=redisDao.getSeckill(seckillId);
        if(secKill==null){
            secKill=seckillDao.queryById(seckillId);
            if(secKill==null){
                return new Exposer(false,seckillId);
            }else
                redisDao.setSeckill(secKill);
        }
        //将暴露的地址使用redis缓存优化。
        Date startTime=secKill.getStartTime();
        Date endTime=secKill.getEndTime();
        Date newTime=new Date();

        if(newTime.getTime()<startTime.getTime()||
            newTime.getTime()>endTime.getTime()){
            return new Exposer(false,seckillId,newTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        String md5=getSalt(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    private String getSalt(long seckillId){
        String md5=seckillId+'/'+salt;
        return DigestUtils.md5DigestAsHex(md5.getBytes());
    }

    @Override
    @Transactional
    /*
    * 使用注解控制事务方法的优点。
    * ①：开发团队达成一致的约定，明确标注事务方法的编程风格。
    * ②：保证事务方法的执行时间尽可能的短。避免一些长时间的操作在这里面执行，如果有http/等相关的长时间操作，可以从里面剥离出来。
    * ③：不是所有的方法都需要事务，如只有一条修改操作、查询等只读操作不需要事务操作。
    * */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException {
        if(md5==null||!md5.equals(getSalt(seckillId))){
            throw new SeckillException("seckill data rewrite");
        }
        Date nowTime=new Date();
        try{
            int insertCount=successKillDao.insertSuccessKilled(seckillId,userPhone);
            if(insertCount<=0){
                throw new RepeatKillException("seckill repeated");
            }else{
                int updateCount=seckillDao.reduceNumber(seckillId,nowTime);
                if(updateCount<=0){
                    throw new SeckillCloseException("seckill is close");
                }else{
                    SuccessKill successKill=successKillDao.queryByIdWithSecKill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnums.SUCCESS,successKill);
                }
            }
        }catch (SeckillCloseException e1){//先判断是不是这类异常，是就输出这类异常的信息，定位准确
            throw e1;
        }
        catch (RepeatKillException e2){//先判断是不是这类异常，是就输出这类异常的信息，定位准确
            throw e2;
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            //所有编译期异常  转化为运行期异常。
            throw new SeckillException("seckill inner error:"+e.getMessage());
        }
    }

    @Override
    public SeckillExecution executeSeckill2(long seckillId, long userPhone, String md5) {
         if(md5==null||!md5.equals(getSalt(seckillId))){
             throw new SeckillException("seckill data rewrite");
         }
         Date killdate=new Date();
         Map<String,Object> map=new HashMap<>();
         map.put("seckillId",seckillId);
         map.put("phone",userPhone);
         map.put("killTime",killdate);
         map.put("result",null);
         try{
             seckillDao.killByProcedure(map);
             int result=MapUtils.getInteger(map,"result",-2);
             if(result==1){
                 SuccessKill sk=successKillDao.queryByIdWithSecKill(seckillId,userPhone);
                 return new SeckillExecution(seckillId,SeckillStateEnums.SUCCESS,sk);
             }else{
                 return new SeckillExecution(seckillId,SeckillStateEnums.stateof(result));
             }
         }catch(Exception e){
             logger.error(e.getMessage(),e);
             return new SeckillExecution(seckillId,SeckillStateEnums.INNER_ERROR);
         }
    }
}
