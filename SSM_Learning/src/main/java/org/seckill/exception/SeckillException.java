package org.seckill.exception;

/**
 * 秒杀相关的异常
 * @author yangxin
 * @version 1.00
 * @time 2018/12/7  9:29
 */
public class SeckillException extends RuntimeException{
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
