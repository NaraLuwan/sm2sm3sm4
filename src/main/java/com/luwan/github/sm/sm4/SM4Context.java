package com.luwan.github.sm.sm4;

/**
 * @author luwan
 * @date 2019/5/12
 */
public class SM4Context {
    /**
     * 加密还是解密
     */
    public int mode;

    /**
     * 子秘钥
     */
    public long[] sk;

    /**
     * 是否填充
     *
     * @see SM4 padding
     */
    public boolean isPadding;

    public SM4Context() {
        this.isPadding = true;
        this.sk = new long[32];
    }
}
