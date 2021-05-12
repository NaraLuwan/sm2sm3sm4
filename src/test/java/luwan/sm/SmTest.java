package luwan.sm;

import luwan.sm.base.BaseUtil;
import luwan.sm.base.Constant;
import luwan.sm2.SM2Util;
import luwan.sm3.SM3Util;
import luwan.sm4.SM4Util;
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
        assert decryptBytes != null;
        String decrypt = new String(decryptBytes, Constant.DEFAULT_CHARSET);

        Assert.assertEquals(ORIGIN_STR, decrypt);
    }

    @Test
    public void sm3() {
        Assert.assertEquals(SM3Util.generateSM3(ORIGIN_STR), SM3Util.generateSM3(ORIGIN_STR));
    }

    @Test
    public void sm4() {
        // test key
        byte[] secretKey = BaseUtil.hexStringToBytes("cbc2f3fbffffffffffffffff00000000");
        String encryptCBC = SM4Util.encryptCBC(secretKey, ORIGIN_STR);

        String decryptCBC = SM4Util.decryptCBC(secretKey, encryptCBC);
        Assert.assertEquals(ORIGIN_STR, decryptCBC);
    }
}
