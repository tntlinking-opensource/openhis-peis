/**
 * @StringUtil.java
 * @com.lingnet.util
 * @Description：
 * 
 * @author xuhp 
 * @copyright  2019
 * @version V
 * @since 2019年3月1日
 */
package com.center.medical.center.qingdao.profession.utils;

/** 
 * @ClassName: StringUtil 
 * @Description: TODO 
 * @author xuhp
 * @date 2019年3月1日 下午4:45:56 
 *  
 */

public class StringUtil {

	public static boolean isNum(Object obj){
		if(obj==null)return false;
		Boolean strResult = obj.toString()
				.matches("(-?[0-9]+\\.[0-9]+)|(-?[0-9]+)");  
		if(strResult == true) {  
           return true; 
        } else {  
           return false;
        }  
	}
	/**
	 * 按字节数截取字符串
	 * 当中文字符的字节只取了一半时，忽略这个中文字符
	 * @return
	 * @throws Exception
	 */
	public static String subBytes(String str, int subStrLength) throws Exception
    {
		if (null == str) {
			return "";
		}
		// 截取字节数
		int tempSubLength = subStrLength;
		// 截取的子串
		String subStr = str.substring(0, str.length() < subStrLength ? str.length() : subStrLength);
		// 截取子串的字节长度
		int subStrByetsL = subStr.getBytes("gbk").length;
		// 比较截取后的字符串字节数大于需要截取的自己数，说明截取的字符串中包含有汉字并且截取后的字节数大于需要的字节数
		while (subStrByetsL > tempSubLength) {
			int subSLengthTemp = --subStrLength;
			// 再少截取一个字符
			subStr = str.substring(0, subSLengthTemp > str.length() ? str.length() : subSLengthTemp);
			subStrByetsL = subStr.getBytes("gbk").length;
		}
		return subStr;
    }
	
	public static void main(String[]args){
		System.out.println(isNum("0.55"));
		System.out.println(isNum("-0.55"));
		System.out.println(isNum("15"));
		System.out.println(isNum("a"));
		System.out.println(isNum("+"));
	}
}
