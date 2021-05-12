## 1. 项目简介

基于国密算法（SM2+SM3+SM4）实现的安全通信协议加解密。

## 2. 算法介绍 

### 2.1 SM2

#### 2.1.1 秘钥格式

在本项目中 , SM2算法中秘钥都是在DER编码下输出的 , SM2秘钥的组成部分有：私钥D 、公钥X 、 公钥Y，他们都可以用长度为64的16进制的HEX串表示。
在加解密调用的时候都会将hexString转换成byte[]后再作为参数传入。其中SM2公钥并不是直接由X+Y表示，而是额外添加了一个头，
比如在硬件加密机中这个头为:"3059301306072A8648CE3D020106082A811CCF5501822D03420004"，软加密中的公钥头为"04"。
头的具体表示信息如下

```
30  (SEQUENCE TAG: SubjectPublicKeyInfo)
59  -len 
30  (SEQUENCE TAG: AlgorithmIdentifier)
13  (SEQUENCE LEN=19)
06  (OID TAG: Algorithm)
07 - len
2A8648CE3D0201    (OID VALUE="1.2.840.10045.2.1": ecPublicKey/Unrestricted Algorithm Identifier) -- 
06  TAG: ECParameters:NamedCurve
08 -len
2A811CCF5501822D  (OID VALUE="1.2.840.10045.3.1.7": 国密新曲线--Secp256r1/prime256v1)  -- 变量
03 - STRING TAG: SubjectPublicKey:ECPoint
42 - len 66
00 - 填充bit数为0
04 - 无压缩 就代表公钥的 , 还需要有一个Head
```

#### 2.2 SM3

SM3摘要结果为256位，比平时使用的hash长128位，所以如果验签采用RSA算法的话需要验256位。

#### 2.3 SM4
 
##### 2.3.1 秘钥格式
  
SM4秘钥长度为32位的hex串，可以直接使用UUID随机生成的秘钥串，最好由客户端动态生产，保证每次对称加密的秘钥都是不同且是跟设备相关的。
 
##### 2.3.2 ECB模式和CBC模式
 
SM4加解密涉及到ECB模式和CBC模式，ECB模式简单有利于计算，但是存在被攻击的可能，CBC模式更加安全，需要在加解密的过程中需要传入一个IV值，需要和客户端约定保持统一。
在本项目中IV值均设置为16进制下的字符串:"31313131313131313131313131313131"，其实就是UTF-8下的16个"1" 通过getBytes[].toHexString()得来的，这个值可以根据需要修改。
 
在SM4加密算法中，要求原始数据长度必须是长度为32的整数倍hex串，但是在实际情况中数据长度并不能保证这么长，这里就涉及到了原始数据填充的问题。
在类SM4.java文件中padding()方法使用基于PBOC2.0的加解密数据填充规范，在数据后填充对应缺少位数个值，每个值均是该数值的hex表示，来解决分组时长度不足的问题。
如：缺少15位则填充15位0x0f。在解密时需要根据最后一个字节去掉对应填充的数据。

#### 2.4 Quick Start

- 示例代码详见： [SmTest](https://github.com/NaraLuwan/sm2sm3sm4/blob/master/src/test/java/com/luwan/github/sm/SmTest.java)

## 3. 项目结构
```text
sm2sm3sm4
└─src
    ├─main
    │  └─java
    │      └─luwan
    │          ├─sm
    │          │  └─base
    │          │          BaseUtil.java
    │          │          Constant.java
    │          │          ErrorCode.java
    │          │          SmException.java
    │          │
    │          ├─sm2
    │          │      Cipher.java
    │          │      SM2.java
    │          │      SM2Key.java
    │          │      SM2Sign.java
    │          │      SM2SignFactory.java
    │          │      SM2SignResult.java
    │          │      SM2SignUtil.java
    │          │      SM2Util.java
    │          │
    │          ├─sm3
    │          │      SM3Util.java
    │          │
    │          └─sm4
    │                  SM4.java
    │                  SM4Context.java
    │                  SM4Util.java
    │
    └─test
        └─java
            └─luwan
                └─sm
                        SmTest.java
```