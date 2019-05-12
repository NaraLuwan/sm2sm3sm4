package com.luwan.github.sm.sm.base;

/**
 * 异常包装
 *
 * @author luwan
 * @date 2019/5/12
 */
public class SmException extends RuntimeException {

    public SmException() {
        super();
    }

    public SmException(Throwable e) {
        super(e);
    }

    public SmException(String msg, Throwable e) {
        super(msg, e);
    }

}
