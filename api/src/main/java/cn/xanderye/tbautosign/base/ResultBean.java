package cn.xanderye.tbautosign.base;

import cn.xanderye.tbautosign.enums.ErrorCode;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 * @author XanderYe
 * @date 2019-01-10
 */
@Data
@Accessors(chain = true)
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

    /**
     * 成功
     * @return ResultBean
     * @author XanderYe
     * @date 2019/9/16
     */
    public static <T> ResultBean success(T data) {
        return new ResultBean<T>().setData(data);
    }

    /**
     * 失败
     * @param errorCode
     * @return ResultBean
     * @author XanderYe
     * @date 2019/9/16
     */
    public static ResultBean error(ErrorCode errorCode) {
        return new ResultBean(errorCode.getCode(), errorCode.getMessage());
    }
}