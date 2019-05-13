package com.luwan.github.sm.sm4;

import com.luwan.github.sm.sm.base.Constant;
import com.luwan.github.sm.sm.base.BaseUtil;
import com.luwan.github.sm.sm.base.ErrorCode;
import com.luwan.github.sm.sm.base.SmException;
import org.apache.commons.codec.binary.Base64;

/**
 * @author luwan
 * @date 2019/5/12
 */
public class SM4Util {

    public static String iv = "31313131313131313131313131313131";   // test 可根据需要修改

    private SM4Util() {
    }

    /**
     * CBC模式加密
     *
     * @param secretKey
     * @param decData
     * @return
     */
    public static String encryptCBC(byte[] secretKey, String decData) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.mode = SM4.SM4_ENCRYPT;

            SM4 sm4 = new SM4();
            sm4.sm4_setKey_enc(ctx, secretKey);
            byte[] encrypted = sm4.sm4_crypt_cbc(ctx, BaseUtil.hexStringToBytes(iv), decData.getBytes(Constant.DEFAULT_CHARSET));

            return Base64.encodeBase64String(encrypted);
        } catch (Throwable e) {
            throw new SmException(ErrorCode.SM4_ENCRYPT_FAILED.getMsg(), e);
        }

    }

    /**
     * CBC模式解密
     *
     * @param secretKey
     * @param encData
     * @return
     */
    public static String decryptCBC(byte[] secretKey, String encData) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.mode = SM4.SM4_DECRYPT;

            SM4 sm4 = new SM4();
            sm4.sm4_setKey_dec(ctx, secretKey);
            byte[] decrypted = sm4.sm4_crypt_cbc(ctx, BaseUtil.hexStringToBytes(iv), Base64.decodeBase64(encData));
            return new String(decrypted, Constant.DEFAULT_CHARSET);
        } catch (Throwable e) {
            throw new SmException(ErrorCode.SM4_DECRYPT_FAILED.getMsg(), e);
        }
    }

}
