package com.center.medical.common.utils;

import java.math.BigDecimal;
import java.util.Random;

/**
 * 操作数字
 * v1被除数
 * v2除数
 *precise 保留小数位数
 */
public class NumUtil {
	public static double DrivedF(Double v1,Double v2,int precise){
        if(v1==null)
            v1=0D;
        if(v2==null || v2==0D)
            throw new RuntimeException("除数为零。");
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal fh=b1.divide(b2,precise, BigDecimal.ROUND_HALF_EVEN);
        Double fhl= fh.doubleValue();
        return fhl;
    }
	
	public static double keepDigit(Double d,int digit) {
		return new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

    /**
     * 生成6位验证码
     */
    public static String createAuthCode(){
        int P=6;
        String[] array = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        Random random = new Random();
        String[] arrayP = new String[P];
        for (int i = 0; i < P; i++) {// 将array进行字符串交换
            int rand = random.nextInt(10);
            String tem = array[rand];
            arrayP[i] = tem;
        }
        String code = "";
        for (int j = 0; j < P; j++) {
            code = arrayP[j] + code;
        }
        return code;
    }
	
	public static void main(String[]args) {
		System.out.println(keepDigit(1.777,2));
	}
}
