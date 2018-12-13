package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.SecKill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在"使用者"的角度设计接口。
 * 三个方面：①：方法定义粒度。②：参数：越简练越好。③返回类型：return 的类型友好/也可以抛出异常。
 * @author yangxin
 * @version 1.00
 * @time 2018/12/7  8:58
 */
public interface SeckillService {

    /**
    * 查询所有秒杀记录。
    * @author      yangxin
     * @param
    * @return
    * @exception
    * @date        2018/12/7 9:02
    */
    List<SecKill> getSeckillList();

    /**
    * 根据Id查询一个秒杀接口。
    * @author      yangxin
     * @param @seckillId:商品ID
    * @return
    * @exception
    * @date        2018/12/7 9:04
    */
    SecKill getById(long seckillId);

    /**
    * 秒杀开启时：输出秒杀接口的地址，否则输出系统时间和秒杀时间。
     * 防止Url规则拼出秒杀地址，再根据浏览器插件，来秒杀。
    * @author      yangxin
    * @return
    * @exception
    * @date        2018/12/7 9:05
    */
    Exposer exportSeckillUrl(long seckillId);

    /**
    *执行秒杀操作。
     * 使用Md5进行判断用户是否串改md5加密的地址来是否进行秒杀。
    * @author      yangxin
    * @return   封装的秒杀结果的数据
    * @date        2018/12/7 9:15
    */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
        throws SeckillException, RepeatKillException, SeckillCloseException;

    /**
    * 使用存储过程
    * @author      yangxin
    * @date        2018/12/12 13:08
    */
    SeckillExecution executeSeckill2(long seckillId, long userPhone, String md5);


}
