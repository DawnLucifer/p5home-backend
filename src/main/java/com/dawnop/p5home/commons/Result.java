package com.dawnop.p5home.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    //成功
    private static final int OK_CODE = 200;
    private static final String OK_MSG = "OK";

    //失败
    private static final int FAILED_CODE = 500;
    private static final String FAILED_MSG = "FAILED";

    public static final int NO_AUTH_CODE = 401;
    public static final String NO_AUTH_MSG = "没有权限";
    public static final String NO_TOKEN = "没有认证";
    public static final String TOKEN_EXPIRE = "token过期";


    private int code;
    private String msg;
    private T data;

    public static <T> Result success(T data) {
        return new Result<>(OK_CODE, OK_MSG, data);
    }

    public static <T> Result failed(String msg) {
        return new Result<>(FAILED_CODE, msg, null);
    }

    public static <T> Result failed() {
        return new Result<>(FAILED_CODE, FAILED_MSG, null);
    }

    public static <T> Result<T> noAuth(String msg) {
        if (!StringUtils.isNoneEmpty(msg)) {
            return new Result<>(NO_AUTH_CODE, msg, null);
        }
        return new Result<>(NO_AUTH_CODE, NO_AUTH_MSG, null);
    }
}
