/**
 * @DesUtil.java
 * @com.lingnet.util
 * @Description：
 * 
 * @author xuhp 
 * @copyright  2018
 * @version V
 * @since 2018年10月10日
 */
package com.center.medical.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.SecureRandom;

/** 
 * @ClassName: DesUtil 
 * @Description: 3des解密
 * @author xuhp
 * @date 2018年10月10日 下午4:13:03 
 *  
 */

public class DesUtil {
	public final static String KEY="oWSSOjegCli3ybZT3fFllcsb";//平安好医生秘钥
	private final static String charset="utf-8";
	public final static String AILLKEY="SFD2sd4eDF2wer6565SDwer1";//微信商城秘钥、海康秘钥

    public final static String HKKEY="DB893E1407C52E62D5336026E877A860";

    //这个只有一个解密方法
	 public static String decode(String message) throws Exception{
		SecretKey secretKey = new SecretKeySpec(KEY.getBytes(charset), "DESede");
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
//		System.out.println("decoded:"+URLDecoder.decode(message,charset));
		byte[] decryptPlainText = cipher.doFinal(
//				message.getBytes(Charset.forName(charset))
				new Base64().decode(URLDecoder.decode(message,charset).getBytes(Charset.forName(charset)))
				);
		return new String(decryptPlainText,charset);//这里一定要加编码,否则可能出乱码
	}
	 
	 /**
     * 测试方法
     */
    public static void main(String[] args) throws Exception {
        /**
         * 这种有加密有解密
         */
//        String str = "你好,goldlone";
////        //密码最好是8的倍数
//        String password = KEY;
////
//        String encryStr =  DesUtil.encrypt(str, password);
//        System.out.println("加密结果："+encryStr);
//
//        String decryStr = DesUtil.decrypt(encryStr, password);
//        System.out.println("解密结果："+decryStr);


//        String s="VCokXijaWgvcJ7KuQZXinb51jesczPbvODbLtmtDNIhfNmIL9AFVuiNknPi5eOguk2oJKwF6rS3yRGc/tA3ApopIyBULOFC45TeEZm4hGcPa/0QSMbe9wfERgmxkCbVZRl2ICqxaXG1sukKpR+sSEJ0EpPoqKCWSB6DcAnbbF7rvsKw0UsdPmUS9NQDkXbaybKFX6BSYSyzN14fvJ0w0gcIdCs0e9Yq7qxyBUyJhALT9BboyHUrL6P67RlTrNizozjUD62V8XC42jlpZsZ3FsulJvXn9qcPqLvU+3MBdpG+plp0kQYwo7vIeBCWUMAUmbOaayO5MimOIxLDpzXlC6e36/N+ceyzmUth6LpzeIt53rnshesZ2y+/E9ESsGcueky1b56HisvZLr7vyWm7uLb8qoyaRC1iclPsIJssh2ABrwHBgE1TWEzzKTf97yKt1UlQRN3h6oagO46Gz072deZNr4TldOlLw/2Ak5eJMLveRhfAus19cnH5qVpeCa8/j";
//        System.out.println(DesUtil.decrypt(s, AILLKEY));



String s="1k%2Bc1zLd0ZvB1ctFqO6%2BaSbC4q%2FGT3Ht03PK5Nl%2Biv5PAhfN3MWG8I%2BtzNafWkog0OeTgf%2BjZJnyI%2BIqgJaT7pGDDrBiC%2B6D5ZuiM1ZcxfqTjl4inMGhwGdBXTZpxgqFjugdazFwEjbhs1P26zwHqzVgGsd63dalYb6eqAbiVx5MylWpfmmrh73w9%2B3iktLvzqtFiDLXAZBS%2BtfIfI7jOm3buQfq5EhR3zBEX%2BVN6a2Mww6rcWVhIMckcwIhGb%2FdUZxAnXJ6diqbI4qWhg%2FYx0FzBnkl1TXcpLXJ%2FJ%2Bvl%2BbCmf5Rrw0YBkPihfq71GZei6cG2YlRCjDX9ye8nGtW5FdVyynHfOkhwl9pYuUEWPsZS8Z%2FeUIYXrneIF28JaWkWsNPPvOu48lxnsmozQruL1C%2BC%2B1u63ngkcK8%2FyoLV6JKNUfF8QHZSZIhCX%2B%2BUilXJnkYq9lANGzLrBCxjB8112numVDsV0ade191kXQB3RlRmv8M9mCeZTqJ4Fgx9PMSX3r6JMtF2lrELhv1q2BpUA%3D%3D";

        String decryStr2 = DesUtil.decode(s);
        System.out.println("解密结果："+decryStr2);
//        System.out.println("解密结果："+decryStr2);
//        String s="dEUNN3V9SPYfPtGf/6EsnwL7m8iHDJYz0tovnqT0ore76rr8lWNX+xSwMSLgwPzwzbZLkONBgu3g6V2WQiJc6ubFuL3SgMQvBz0tQefbCECJUTJqvvT6EFmdLeNe4jQRMCnMhwMWub/kMD3BXt8mk67qtynDNYL9cAerdAPHg7IkPq64tKMf9d9EnmNyLmwrNpb+w/qLoUP5CCw+mzmEqU1F3v163YzbxaZ5+49gPAQD603JNZ8P7C2EmzanGbaCDGz4Q6ikKA/ZSWoeJczkRRZJ1SWbX1b0rjM+wKNzO5ECQKzpFENdvDFPxRjU/Jh8u3D7UWG0E/mZbZ03bhbH5n5hTHcCIHExKVP6FVL3ntMXnyYEttR2ZyuRE5C0KV9qerot4uZF/BIFoF/pSIjtHwL7m8iHDJYz0tovnqT0orfOtNMIpYeJUgILPdexf3tdH3ZntuhfoVpo30PZ2n6waA==";
//        System.out.println(decrypt(s,KEY));
//        String s="dEUNN3V9SPYfPtGf/6EsnwL7m8iHDJYz0tovnqT0ore76rr8lWNX xSwMSLgwPzwzbZLkONBgu3g6V2WQiJc6ubFuL3SgMQvBz0tQefbCECJUTJqvvT6EFmdLeNe4jQRMCnMhwMWub/kMD3BXt8mk67qtynDNYL9cAerdAPHg7IkPq64tKMf9d9EnmNyLmwrNpb w/qLoUP5CCw mzmEqU1F3v163YzbxaZ5 49gPAQD603JNZ8P7C2EmzanGbaCDGz4Q6ikKA/ZSWoeJczkRRZJ1SWbX1b0rjM wKNzO5ECQKzpFENdvDFPxRjU/Jh8u3D7UWG0E/mZbZ03bhbH5n5hTHcCIHExKVP6FVL3ntMXnyYEttR2ZyuRE5C0KV9qerot4uZF/BIFoF/pSIjtHwL7m8iHDJYz0tovnqT0orfOtNMIpYeJUgILPdexf3tdH3ZntuhfoVpo30PZ2n6waA==";
//          String s="dEUNN3V9SPYfPtGf/6EsnwL7m8iHDJYz0tovnqT0ore76rr8lWNX+xSwMSLgwPzwzbZLkONBgu3g6V2WQiJc6ubFuL3SgMQvBz0tQefbCECJUTJqvvT6EFmdLeNe4jQRMCnMhwMWub/kMD3BXt8mk67qtynDNYL9cAerdAPHg7IkPq64tKMf9d9EnmNyLmwrNpb+w/qLoUP5CCw+mzmEqU1F3v163YzbxaZ5+49gPAQD603JNZ8P7C2EmzanGbaCDGz4Q6ikKA/ZSWoeJczkRRZJ1SWbX1b0rjM+wKNzO5ECQKzpFENdvDFPxRjU/Jh8u3D7UWG0E/mZbZ03bhbH5n5hTHcCIHExKVP6FVL3ntMXnyYEttR2ZyuRE5C0KV9qerot4uZF/BIFoF/pSIjtHwL7m8iHDJYz0tovnqT0orfOtNMIpYeJUgILPdexf3tdH3ZntuhfoVpo30PZ2n6waA==";
//        System.out.println(URLDecoder.decode(s, "UTF-8"));
//          System.out.println(
//                decode(s)
//                decrypt(URLDecoder.decode(s, "UTF-8"),KEY)
//        );
        //       String s=encrypt("abc",KEY);
//        try {
//            System.out.println(DesUtil.decode(s));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    	System.out.println(decrypt("LK09scXqT4vpvvVz0rVbWYpIbDAxnZssPUfhMzoiJeQ0UmeG9LWB0dJmuxYav/FVwXfJsP9VUazvOkxHBYlv+rH2kDVqKs5XkXUZSRrQG1z+Xy/NRONOuDpg4+oKRjC5zsMfS2K5/6rfJ9IlT4bxGsBBB4GTNRYYjniM/Jrr4r5iE/9LUUq5BZrv+yXwNsPe2tU76UF+WwiWeW7AshWTl6pZGgJqvDM9tsAZPLnbFvCuvKMXoBE9BwmjZvt1rWflvzq8ffHI4y7l0kj+48h3Ju7gSZOhzflpSV0OxQDmbXREAGr6M0qVXyJvAfn4cjwvzQB1nAkLnzC2wBk8udsW8K68oxegET0HHBvnAk+rOciprw5HsbHFxty9Pq82lqL6s7YkKCNfzKRHcUs0/+FZB668oxegET0HHBvnAk+rOciprw5HsbHFxrkMrUZNmlMD21WxxjCOZ3ZECdsV+FtiV8drB3y/y/+4lhSJLNeikgaCcJpPjOymlfqE0rP4E6d0oQJFNAFfqQ0QmKu5PsxGNu8h9gTH9GZlhvnoguMSvNddFhEY2r5YQLbAGTy52xbwrryjF6ARPQdvUy18G0t1+W7qE3zuhnfiFSb6hMqcKWjUFd6aXurWvkdxSzT/4VkHrryjF6ARPQccG+cCT6s5yKmvDkexscXGuQytRk2aUwPbVbHGMI5ndkQJ2xX4W2JXx2sHfL/L/7iWFIks16KSBoJwmk+M7KaVA9amR8BFwZOhAkU0AV+pDRCYq7k+zEY27yH2BMf0ZmWMA68qdM8A/nIjUkLd9Mpe+rf5E8pnbesdV8cuzhSB5FNRfQfExHqNsxC/m2tE/o6voAzaP2kuhL6udK/XVf28", AILLKEY));
    }
    
    /**
     * 进行加密操作
     * 参数一：待加密的字符串，参数二：加密密钥
     * 返回经过Base64编码后的字符串
     * 编码格式为UTF-8
     */
    public static String encrypt(String encryptionStr, String password) {
        try{
            byte[] encryptionBytes = encryptionStr.getBytes("UTF-8");
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            // 创建一个密钥工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密钥初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            // 执行加密操作   
            byte[] encryptionBase64Bytes = new Base64().encode(cipher.doFinal(encryptionBytes));
            // 转换为字符串返回
            return new String(encryptionBase64Bytes);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 进行解密操作
     * 参数一：待解密的字符串，参数二：加密密钥
     * 返回解密后的字符串
     */
    public static String decrypt(String decryptionBase64Str, String password) {
        try {
            byte[] decryptionbytes = new Base64().decode(decryptionBase64Str);
            // DES算法要求有一个可信任的随机数源
            SecureRandom random = new SecureRandom();
            // 创建一个DESKeySpec对象
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            // 创建一个密钥工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密钥初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);
            // 开始解密操作
            return new String(cipher.doFinal(decryptionbytes), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
	 
	 public static void main2(String[] args) {
		try {
//			String encodestr=encode("你好你好你好");
//			System.out.println(encodestr);
//			System.out.println(decode(encodestr));
//            String s="dEUNN3V9SPYfPtGf/6EsnwL7m8iHDJYz0tovnqT0ore76rr8lWNX xSwMSLgwPzwzbZLkONBgu3g6V2WQiJc6ubFuL3SgMQvBz0tQefbCECJUTJqvvT6EFmdLeNe4jQRMCnMhwMWub/kMD3BXt8mk67qtynDNYL9cAerdAPHg7IkPq64tKMf9d9EnmNyLmwrNpb w/qLoUP5CCw mzmEqU1F3v163YzbxaZ5 49gPAQD603JNZ8P7C2EmzanGbaCDGz4Q6ikKA/ZSWoeJczkRRZJ1SWbX1b0rjM wKNzO5ECQKzpFENdvDFPxRjU/Jh8u3D7UWG0E/mZbZ03bhbH5n5hTHcCIHExKVP6FVL3ntMXnyYEttR2ZyuRE5C0KV9qerot4uZF/BIFoF/pSIjtHwL7m8iHDJYz0tovnqT0orfOtNMIpYeJUgILPdexf3tdH3ZntuhfoVpo30PZ2n6waA==";
			String s="UazyyWqH+9RlbNe4PACgP2+X+RnORtM394aqVoZp5U0sm9oaAsFpeckNh9xgilHOUqCbBqMFOz1PEaqDUp5RS71uy5DHANBfSCTdDhzoLJv9VZqzVTIP7+IDuTxQzrzznn1obyxTnekusB23ewZni5fv94bfXtIIY7JO0S/BlO+7Kr6vcXK2o+fJ+xjsNf+mH3Yxw8GSKhf8ZY3zG72r0QMikdiUexp9c8FN9aA6jfxtu053iHpZlW3cd8W0MQ6dckkN4E8MQ8kLr6QLWm4veaPyz4szyca83/qA9ytUKzU+hyC1hnH2OJ5VOw8Dcd0TgFmzXxSAdLUqz/CAo2quzOyiPkzU3AJDQxXK7FFKuTzkcwc1ygWcSrLC1ThG1me8y/aetVrWAbunJj1OduSzTip4KjizA6Uv2Xp/VRSi8IyBDBqxvzqqRbsqvq9xcraj9jJed3cYtwkAnEjGJp+KzMABPjHVDS/6f9PIORfIGtua6rRWHbPBGQ==";
			System.out.println(
                    DesUtil.decrypt(s,DesUtil.HKKEY)
					);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
