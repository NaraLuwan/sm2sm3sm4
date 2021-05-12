package luwan.sm3;

import luwan.sm.base.Constant;
import luwan.sm.base.ErrorCode;
import luwan.sm.base.SmException;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.util.encoders.Hex;

/**
 * SM3签名摘要
 *
 * @author luwan
 * @date 2019/5/12
 */
public class SM3Util {

    private SM3Util() {
    }

    public static String generateSM3(String srcData) {
        try {
            SM3Digest sm3 = new SM3Digest();
            byte[] md = new byte[32];
            byte[] dataBytes = srcData.getBytes();
            sm3.update(dataBytes, 0, dataBytes.length);
            sm3.doFinal(md, 0);
            return new String(Hex.encode(md), Constant.DEFAULT_CHARSET);
        } catch (Throwable e) {
            throw new SmException(ErrorCode.SM3_HASH_FAILED.getMsg(), e);
        }
    }
}
