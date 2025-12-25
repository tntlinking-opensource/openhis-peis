package com.center.medical.common.utils.aes;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author: 路飞
 * @date: 2023-02-08 17:00
 * @description: 对称加密AES实现
 */
public class AESUtils {
    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

        return new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(sSrc.replaceAll(" +", "+"));//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, "utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
        String cKey = "1234567890123456";
        // 需要加密的字串
        String cSrc = "www.gowhere.so";
        System.out.println(cSrc);
        // 加密
        String enString = AESUtils.Encrypt(cSrc, cKey);
        System.out.println("加密后的字串是：" + enString);

        // 解密
        String DeString = AESUtils.Decrypt("ls8m1fivRrleULHWcMsXlC+0f6913mqUUt4FwJaDZ/NHNMWp41PYLJCN2WuETiINEX/QYwEH8IyT5d0RV9uP/9Usdx04Ef+JFj172i4UBjCLGimyW1h+PjfSdG/1poW+cPgYfI1+Wm6dBdr8q/1iMBxc1MEQmI0Zw7W/YACYzjRxg4+CXIeeknX2sxq+O4hhWbOs38mqTK6C0ukN5B1W0tdkbxUfOQZvX/pUMXlKHCWWHPhgzdlD4TgaUPHSapjbWw17xgf8XlqIi91Sh6nGvvsDagh/X1ukB6gJLdlQ/TaemSUbl1dpO1q3JHaLH94o", "146604D44F7E91CE");
        System.out.println("解密后的字串是：" + DeString);
    }
}
