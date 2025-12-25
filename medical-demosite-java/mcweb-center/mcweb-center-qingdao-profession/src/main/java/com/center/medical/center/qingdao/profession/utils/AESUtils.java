package com.center.medical.center.qingdao.profession.utils;

import org.apache.commons.codec.binary.Base64;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;

/**
 *AESUtils
 *aes 对称加密解密工具类, 注意密钥不能随机生机, 不同客户端调用可能需要考虑不同Provider,
 *考虑安卓与 IOS 不同平台复杂度,简化不使用 Provider
 */
public class AESUtils {
    /*
     *使用 PKCS7Padding 填充必须添加一个支持 PKCS7Padding 的 Provider
     *类加载的时候就判断是否已经有支持 256 位的 Provider,如果没有则添加进去
     */ static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) { Security.addProvider(new BouncyCastleProvider());
        }
    }

    /**
     *加密 128 位
     *
     *@param content 需要加密的原内容
     *@param pkey	密匙
     */
    public static byte[] aesEncrypt(byte[] content, String pkey,String IV) { try {
        //SecretKey secretKey = generateKey(pkey);
        //byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec skey = new SecretKeySpec(pkey.getBytes(), "AES"); Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");// "算法/加密/填充"
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes()); //初始化加密器
        cipher.init(Cipher.ENCRYPT_MODE, skey, iv);//初始化加密器
        return cipher.doFinal(content);
    } catch (Exception e) {
    //System.out.println("aesEncrypt()	method error:"+e.getLocalizedMessage());
    }
        return null;
    }
    /**
     *获得密钥
     */
    private static SecretKey generateKey(String secretKey) throws Exception {
        //防止 linux 下 随机生成 key
        Provider p = Security.getProvider("SUN");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", p); secureRandom.setSeed(secretKey.getBytes());
        KeyGenerator kg = KeyGenerator.getInstance("AES"); kg.init(secureRandom);
        // 生成密钥
        return kg.generateKey();
    }
    /**
     *@param content 加密前原内容
     *@param pkey	长度为 16 个字符,128 位
     *@return base64EncodeStr	aes 加密完成后内容
     */
    public static String aesEncryptStr(String content, String pkey,String IV) { byte[] aesEncrypt = aesEncrypt(content.getBytes(StandardCharsets.UTF_8),
            pkey,IV);
        return Base64.encodeBase64String(aesEncrypt);
    }
    /**
     *@param content base64 处理过的字符串
     *@param pkey	密匙
     *@return 解密 失败将返回 NULL
     */
    public static String aesDecodeStr(String content, String pkey,String IV) throws Exception {
        try {
            byte[] base64DecodeStr = Base64.decodeBase64(content);
            byte[] aesDecode = aesDecode(base64DecodeStr, pkey,IV);
            if (aesDecode == null) {
                return null;
            }
            return new String(aesDecode, StandardCharsets.UTF_8);}
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("解密异常");
        }
    }
    /**
     *解密 128 位
     *
     *@param content 解密前的 byte 数组
     *@param pkey	密匙
     *@return result	解密后的 byte 数组
     */
    public static byte[] aesDecode(byte[] content, String pkey,String IV) throws Exception {
        //SecretKey secretKey = generateKey(pkey);
        //byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec skey = new SecretKeySpec(pkey.getBytes(), "AES");
        IvParameterSpec	iv = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        // 创建密码器
        cipher.init(Cipher.DECRYPT_MODE, skey, iv);
        // 初始化解密器
        return cipher.doFinal(content);
    }

    public static void main(String[] args) throws Exception {
        //明文
        String content = "2019030900000020";
        //默认向量常量
        String IV = "1234567890123456";
        //密匙
        String pkey = "O3uNuCWJ3Dh7x6kY";
        System.out.println("待加密报文:" + content);
        System.out.println("密匙:" + pkey);
        String aesEncryptStr = AESUtils.aesEncryptStr(content, pkey,IV);
        System.out.println("加密报文:" + aesEncryptStr);
        String aesDecodeStr = AESUtils.aesDecodeStr(aesEncryptStr, pkey,IV);
        System.out.println("解密报文:" + aesDecodeStr);
        System.out.println("	加	解	密	前	后	内	容	是	否	相	等	:"	+ aesDecodeStr.equals(content));}
}
