package luwan.sm2;

import luwan.sm.base.BaseUtil;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * @author luwan
 * @date 2019/5/12
 */
public class SM2Key {

    ECPoint publicKey;
    BigInteger privateKey;

    //SoftPubKey:04+X+Y
    public static final String pubSoftKeyHead = "04";
    //HardPubKey:3059301306072A8648CE3D020106082A811CCF5501822D03420004+X+Y
    public static final String pubHardKeyHead = "3059301306072A8648CE3D020106082A811CCF5501822D034200";

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(BigInteger privateKey) {
        this.privateKey = privateKey;
    }

    public ECPoint getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(ECPoint publicKey) {
        this.publicKey = publicKey;
    }


    public String getPubHexInSoft() {
        return BaseUtil.byteToHex(publicKey.getEncoded());
    }

    public String getPubHexInHard() {
        return pubHardKeyHead + BaseUtil.byteToHex(publicKey.getEncoded());
    }

    public String getPriHexInSoft() {
        return BaseUtil.byteToHex(privateKey.toByteArray());
    }
}
