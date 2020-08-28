package cn.xanderye.tbautosign.base;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Xander on 2018-11-05.
 */
@Data
public class ResultBean<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String msg = "success";

    private int code = 0;
    private T data;

    public ResultBean() {
    }

    public ResultBean(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public ResultBean(T data) {
        this.data = data;
    }

}