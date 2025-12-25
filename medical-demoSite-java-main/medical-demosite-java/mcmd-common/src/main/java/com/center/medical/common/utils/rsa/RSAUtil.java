package com.center.medical.common.utils.rsa;

import cn.hutool.json.JSONUtil;
import com.center.medical.common.exception.ServiceException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 路飞
 * @date: 2023-02-08 17:04
 * @description: 非对称加密RAS
 */
public class RSAUtil {
    private static final String PUB_KEY = "publicKey";
    private static final String PRI_KEY = "privateKey";
    private static final String CHARSET = "UTF-8";
    private static final String RSA_ALGORITHM = "RSA";
    private static final Integer KEY_LENGTH = 1024;

    public static final Map<String, String> keyMap = createKeys(KEY_LENGTH);

    public static void main(String[] args) {
        Map<String, String> keys = createKeys(1024);
        System.out.println("生成密钥对：" + JSONUtil.toJsonStr(keys));
//        String data = "{\" userName\":\"lianren\",\" password\":\"123456\"}80C8A8EFFC1E8552";
//
//        String s1 = publicEncrypt(data, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDnhdKOupx3NJET8B6t8gOqObECtMpWYt8Y9Kz4V6FORjkrztSICS0TIkn9Ap5amIw4pTIkMquKf2tGkNRDRQW9ZSh4h45a6ymtx3dehbbZiW05uNQV+7H4O+R5uxbblzetoHKM5haf8gQBToPf4JeHLDSQlJV6Xv9PSg8WIglu9wIDAQAB");
//        System.out.println("加密后的信息：" + s1);

//        String s1 = "C7bG50fi0hNqDsbUr5rryKYby8qw/yoOM9bv7ksvrURk75wyW1oDd217yHsgx4gThIxVelxL3YJbBJCC2drwI0FT+ZAoyTIWXPVzaYNTn+HjytpWpz9J3Au5odyXaeLRWbLRxEEBIjQuk1dFPuAK3bWNRVd0RHxKJfCC+xEbv5WfdPhU1SM6fuD8eFwfQgfKwXfXBCTK+ePVJOX9w7EK7HP6";
//        String s2 = privateDecrypt(s1, "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIMBNhNkDf7pN+QqK5L8m1Dop8IeWbCTPUWWIQEKX5Fqks3ALUd7l9jNPnFLGLH/mpjbUhiVhHVVLrwxccpJ9VTjBIvJlF6smqxRAxRiCzWZy7fEPh21ZRhpPnOR9Ahl+/VA70sisZrgMJ5O3JJBldur8WQceqa5pDQbpsxN6oUzAgMBAAECgYAA8INIxFDtYRLLihnh5fnfLKv3UmxmPcgUtazq6v4gdtkyrIwmlRZTsbUe7uDwddrP+dKu5nOau8+n/drJDpq5zH0Q2U6S8TjoeZxfoANTza6sERqsnz+qUucZm8s8lBca+Ny9uI+gOvt1Av3io4WIfcROrzeXb0EYabigjJBL0QJBAMO7WrtDg5lfYxBEQedVJC/kkR32X+CP+aJ+ugiR05GgKIv4ZaVGH0FFFjmX6STr8MkTHEiuapgQfg4xE8C+CIcCQQCrV7bYKYDE4Qiyqope9VnZ2SA8NV8ZTkWvGMYfQVKb4k1E6piGfZIqh1BH+skGyGmiKTNjcXZ++TP5SOjsk8T1AkEAht8H4zffa4Td9yyBmQ0DFXlTQakuYcfzxTO7rwIFHjiVF/X+3IbYLJqVePQjdAyca4GeoLqGuufKZK8OpGaN6wJAN+GNkoZGpu81pP+VzU57lV8I8ln9wAUVGPwdcYgeZxSxWq6gPAysJQ6okjEqavnl6atCRUZ1/QdNehnbTX0MGQJASxvzlp4jinU/r4ySJ0ZbEkYnVApBNwTc3bD1zj6zdrWDkcXce3xD1bC2VzS41E+u8Irmuwb+bXIVncoVKSooMQ==");
//        System.out.println("解密后的信息：" + s2);

    }

    /**
     * 生成秘钥对
     *
     * @param keySize
     * @return Map<String, String>
     */
    public static Map<String, String> createKeys(int keySize) {
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }
        //初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = new String(Base64.encodeBase64(publicKey.getEncoded()));
        //得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = new String(Base64.encodeBase64(privateKey.getEncoded()));
        Map<String, String> keyPairMap = new HashMap<>(1);
        keyPairMap.put(PUB_KEY, publicKeyStr);
        keyPairMap.put(PRI_KEY, privateKeyStr);


        return keyPairMap;
    }

    /**
     * 得到公钥
     *
     * @param keyMap 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static String getPublicKey(Map<String, String> keyMap) {
        return keyMap.get(PUB_KEY);
    }

    /**
     * 得到私钥
     *
     * @param keyMap 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, String> keyMap) {
        return keyMap.get(PRI_KEY);
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicEncrypt(String data, String publicKey) {
        try {
            //通过X509编码的Key指令获得公钥对象
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
            RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return new String(Base64.encodeBase64(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), key.getModulus().bitLength())));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateDecrypt(String data, String privateKey) {
        try {
            //通过PKCS#8编码的Key指令获得私钥对象
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), key.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new ServiceException("解密字符串[" + data + "]时遇到异常");
        }
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateDecrypt1(String data, String privateKey) throws Exception {
            //通过PKCS#8编码的Key指令获得私钥对象
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), key.getModulus().bitLength()), CHARSET);
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) throws IOException {
        int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        out.close();
        return resultDatas;
    }
}
