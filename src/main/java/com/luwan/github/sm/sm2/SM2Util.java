package com.luwan.github.sm.sm2;

import com.luwan.github.sm.sm.base.BaseUtil;
import com.luwan.github.sm.sm.base.ErrorCode;
import com.luwan.github.sm.sm.base.SmException;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * @author luwan
 * @date 2019/5/12
 */
public class SM2Util {
    private static String PRIVATE_KEY;
    private static String PUBLIC_KEY;

    static {
        SM2KeyVO sm2KeyVO = generateKeyPair();
        PRIVATE_KEY = sm2KeyVO.getPriHexInSoft();
        PUBLIC_KEY = sm2KeyVO.getPubHexInSoft();
    }

    private SM2Util() {
    }

    /**
     * 生成随机秘钥对
     *
     * @return
     */
    public static SM2KeyVO generateKeyPair() {
        SM2 sm2 = SM2.Instance();
        AsymmetricCipherKeyPair key = null;
        while (true) {
            key = sm2.ecc_key_pair_generator.generateKeyPair();
            if (((ECPrivateKeyParameters) key.getPrivate()).getD().toByteArray().length == 32) {
                break;
            }
        }
        ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) key.getPrivate();
        ECPublicKeyParameters ecpub = (ECPublicKeyParameters) key.getPublic();
        BigInteger privateKey = ecpriv.getD();
        ECPoint publicKey = ecpub.getQ();
        SM2KeyVO sm2KeyVO = new SM2KeyVO();
        sm2KeyVO.setPublicKey(publicKey);
        sm2KeyVO.setPrivateKey(privateKey);
        return sm2KeyVO;

    }

    /**
     * 加密（采用新版本的分组方式C1 | C2 | C3）
     *
     * @param denData 待加密字符串
     * @return
     */
    public static String encrypt(String denData) {
        try {
            byte[] bytes = denData.getBytes();
            if (bytes == null || bytes.length == 0) {
                return null;
            }

            byte[] source = new byte[bytes.length];
            System.arraycopy(bytes, 0, source, 0, bytes.length);

            Cipher cipher = new Cipher();
            SM2 sm2 = SM2.Instance();
            ECPoint userKey = sm2.ecc_curve.decodePoint(BaseUtil.hexStringToBytes(PUBLIC_KEY));

            ECPoint c1 = cipher.Init_enc(sm2, userKey);
            cipher.Encrypt(source);
            byte[] c3 = new byte[32];
            cipher.doFinal(c3);

            // C1 | C2 | C3
            return BaseUtil.byteToHex(c1.getEncoded()) + BaseUtil.byteToHex(source) + BaseUtil.byteToHex(c3);
        } catch (Throwable e) {
            throw new SmException(ErrorCode.SM2_ENCRYPT_FAILED.getMsg(), e);
        }
    }

    /**
     * 解密（采用新版本的分组方式C1 | C2 | C3）
     *
     * @param encryptedData 待解密字符串
     * @return
     */
    public static byte[] decrypt(String encryptedData) {
        try {
            byte[] bytes = BaseUtil.hexStringToBytes(encryptedData);
            if (bytes == null || bytes.length == 0) {
                return null;
            }
            //加密字节数组转换为十六进制的字符串 长度变为encryptedData.length * 2
            String data = BaseUtil.byteToHex(bytes);
            /** 分组方式
             *  分解加密字串 C1 | C2 | C3
             * （C1 = C1标志位2位 + C1实体部分128位 = 130）
             * （C3 = C3实体部分64位  = 64）
             * （C2 = encryptedData.length * 2 - C1长度  - C2长度）*/

            byte[] c1Bytes = BaseUtil.hexToByte(data.substring(0, 130));
            int c2Len = bytes.length - 97;
            byte[] c2 = BaseUtil.hexToByte(data.substring(130, 130 + 2 * c2Len));
            byte[] c3 = BaseUtil.hexToByte(data.substring(130 + 2 * c2Len, 194 + 2 * c2Len));

            SM2 sm2 = SM2.Instance();
            BigInteger userD = new BigInteger(1, BaseUtil.hexStringToBytes(PRIVATE_KEY));

            //通过C1实体字节来生成ECPoint
            ECPoint c1 = sm2.ecc_curve.decodePoint(c1Bytes);
            Cipher cipher = new Cipher();
            cipher.Init_dec(userD, c1);
            cipher.Decrypt(c2);
            cipher.doFinal(c3);

            return c2;
        } catch (Throwable e) {
            throw new SmException(ErrorCode.SM2_DECRYPT_FAILED.getMsg(), e);
        }
    }
}