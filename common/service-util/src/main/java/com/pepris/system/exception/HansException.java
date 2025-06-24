package com.pepris.system.exception;

import com.pepris.common.result.ResultCodeEnum;
import lombok.Data;

/**
 * 自定义全局异常类
 */
@Data
public class HansException extends RuntimeException {
    private Integer code;

    private String message;

    /**
     * 通过状态码和错误消息创建异常对象
     *
     * @param code
     * @param message
     */
    public HansException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 接收枚举类型对象
     *
     * @param resultCodeEnum
     */
    public HansException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    @Override
    public String toString() {
        return "GuliException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
