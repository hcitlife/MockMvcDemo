package com.hc.bean;

import lombok.Getter;
import lombok.Setter;

public enum ResultCode {
    /**
     * 成功
     */
    OK(200, "OK"),
    /**
     * 服务器错误
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    /**
     * 操作代码
     */
    int code;
    /**
     * 提示信息
     */
    String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}