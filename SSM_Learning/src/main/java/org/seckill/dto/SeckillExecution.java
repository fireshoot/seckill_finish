package org.seckill.dto;

import org.seckill.entity.SuccessKill;
import org.seckill.enums.SeckillStateEnums;

/**
 * 封装秒杀执行后的结果
 * @author yangxin
 * @version 1.00
 * @time 2018/12/7  9:18
 */
public class SeckillExecution {

    private long seckillId;

    private int state;

    private String stateInfo;

    private SuccessKill successKill;

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKill=" + successKill +
                '}';
    }

    public SeckillExecution(long seckillId, SeckillStateEnums seckillStateEnums, SuccessKill successKill) {
        this.seckillId = seckillId;
        this.state = seckillStateEnums.getState();
        this.stateInfo = seckillStateEnums.getStateInfo();
        this.successKill = successKill;
    }

    public SeckillExecution(long seckillId,SeckillStateEnums seckillStateEnums) {
        this.seckillId = seckillId;
        this.state = seckillStateEnums.getState();
        this.stateInfo = seckillStateEnums.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKill getSuccessKill() {
        return successKill;
    }

    public void setSuccessKill(SuccessKill successKill) {
        this.successKill = successKill;
    }
}
