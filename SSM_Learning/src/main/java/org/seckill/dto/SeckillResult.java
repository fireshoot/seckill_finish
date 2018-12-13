package org.seckill.dto;

/**
 *封装成json结果
 *所有的Ajax请求返回类型。
 * @author yangxin
 * @time 2018/12/8  12:34
 */
public class SeckillResult<T> {

    //表示是否拿到数据。
    private boolean success;

    private T data;

    private String errMes;

    public SeckillResult(boolean success, String errMes) {
        this.success = success;
        this.errMes = errMes;
    }

    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrMes() {
        return errMes;
    }

    public void setErrMes(String errMes) {
        this.errMes = errMes;
    }


}
