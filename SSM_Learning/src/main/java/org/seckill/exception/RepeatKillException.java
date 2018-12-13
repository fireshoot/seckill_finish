package org.seckill.exception;

/**
 * 重复秒杀异常(运行期异常)。
 * 异常：运行期异常、编译期异常。运行期异常不需要手动try/catch。
 * spring 的声明式事务只接口运行期异常，如果是编译期异常他是不会做回滚的。
 * @author yangxin
 * @version 1.00
 * @time 2018/12/7  9:23
 */
public class RepeatKillException extends SeckillException{
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
