package com.luwan.github.sm.sm.base;

/**
 * 错误码
 *
 * @author luwan
 * @date 2019/5/12
 */
public enum ErrorCode {

    SM2_DECRYPT_FAILED(1, "SM2解密失败!"),
    SM2_ENCRYPT_FAILED(2, "SM2加密失败!"),
    SM3_HASH_FAILED(3, "SM2加密失败!"),
    SM4_DECRYPT_FAILED(4, "SM4解密失败!"),
    SM4_ENCRYPT_FAILED(5, "SM4加密失败!");

    private int code;
    private String msg;

    ErrorCode(int code, String comment) {
        this.code = code;
        this.msg = comment;
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
