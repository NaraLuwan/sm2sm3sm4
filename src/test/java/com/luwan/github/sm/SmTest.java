package com.luwan.github.sm;

import com.luwan.github.sm.sm.base.BaseUtil;
import com.luwan.github.sm.sm.base.Constant;
import com.luwan.github.sm.sm2.SM2Util;
import com.luwan.github.sm.sm3.SM3Util;
import com.luwan.github.sm.sm4.SM4Util;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author luwan
 * @date 2019/5/12
 */
public class SmTest {

    private static final String ORIGIN_STR = "hello sm!";

    @Test
    public void sm2() {
        String encrypt = SM2Util.encrypt(ORIGIN_STR);

        byte[] decryptBytes = SM2Util.decrypt(encrypt);
        String decrypt = new String(decryptBytes, Constant.DEFAULT_CHARSET);

        Assert.assertTrue(ORIGIN_STR.equals(decrypt));
    }

    @Test
    public void sm3() {
        System.out.println(SM3Util.generateSM3(ORIGIN_STR));
    }

    @Test
    public void sm4() {
        byte[] secretKey = BaseUtil.hexStringToBytes("cbc2f3fbffffffffffffffff00000000");   // test key
        String encryptCBC = SM4Util.encryptCBC(secretKey, ORIGIN_STR);

        String decryptCBC = SM4Util.decryptCBC(secretKey, encryptCBC);
        Assert.assertTrue(ORIGIN_STR.equals(decryptCBC));
    }
}
