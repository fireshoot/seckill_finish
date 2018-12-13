package org.seckill.entity;

import java.sql.Date;

public class SuccessKill {
	
	private long seckillId;
	
	private long userPhone;
	
	private short state;
	
	private Date createTime;

	private SecKill secKill;
	
	//相应的get和set方法
	
     //重写ToString方法   

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(long userPhone) {
		this.userPhone = userPhone;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public SecKill getSecKill() {
		return secKill;
	}

	public void setSecKill(SecKill secKill) {
		this.secKill = secKill;
	}

	@Override
	public String toString() {
		return "SuccessKill [seckillId=" + seckillId + ", userPhone=" + userPhone + ", state=" + state + ", createTime="
				+ createTime + ", secKill=" + secKill + "]";
	}
	
	
	

}
