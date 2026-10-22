package com.keliu.data.common.exception;

import com.keliu.data.common.result.ResultCode;
import lombok.Getter;

/**
 * <h3>自定义业务异常</h3>
 * <p>
 * 业务代码中主动抛出，由全局异常处理器捕获并返回统一格式。
 * </p>
 */
@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** 状态码 */
    private final Integer code;

    public BusinessException(ResultCode rc) {
        super(rc.getMsg());
        this.code = rc.getCode();
    }

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public BusinessException(String msg) {
        super(msg);
        this.code = ResultCode.FAIL.getCode();
    }
}
