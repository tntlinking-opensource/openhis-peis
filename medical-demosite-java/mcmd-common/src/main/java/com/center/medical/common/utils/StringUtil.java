/**
 * @StringUtil.java
 * @com.lingnet.util
 * @Description：
 * @author xuhp
 * @copyright 2019
 * @version V
 * @since 2019年3月1日
 */
package com.center.medical.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xuhp
 * @ClassName: StringUtil
 * @date 2019年3月1日 下午4:45:56
 */

public class StringUtil {
    public static final String SUCCESS = "success";
    public static final String BR = "\n\r";//总检 每条记录间的换行

    public static boolean isNum(Object obj) {
        if (obj == null) return false;
        Boolean strResult = obj.toString().matches("(-?[0-9]+\\.[0-9]+)|(-?[0-9]+)");
        if (strResult == true) {
            return true;
        } else {
            return false;
        }
    }

    public static String addMark(String str) {
        if (str == null) return "";
        str = str.trim();
        if (str.length() == 0) return "";
        if (str.endsWith(",") || str.endsWith("。") || str.endsWith("，")) return str;
        return str + "。";
    }

    public static String addComma(String str) {
        if (str == null) return "";
        str = str.trim();
        if (str.length() == 0) return "";
        if (str.endsWith(",") || str.endsWith("。") || str.endsWith("，")) return str;
        return str + "，";
    }

    public static String testPassword(String password) {
        if (StringUtils.isEmpty(password)) {
            return "密码必填";
        }
        if (password.length() < 8 || password.length() > 16) {
            return "密码长度必须在8~16位之间";
        }
        if (!match(password, "([a-zA-Z]+[0-9]+[!@#$%^&,.*]+)|([a-zA-Z]+[!@#$%^&,.*]+[0-9]+)|([0-9]+[!@#$%^&,.*]+[a-zA-Z]+)|([0-9]+[a-zA-Z]+[!@#$%^&,.*]+)|([!@#$%^&,.*]+[a-zA-Z]+[0-9]+)|([!@#$%^&,.*]+[0-9]+[a-zA-Z]+)")) {
            return "密码必须由字母、数字和特殊字符混合组成";
        }
        return SUCCESS;
    }

    public static boolean match(String str, String regix) {
        Pattern p = Pattern.compile(regix);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * in 不能超过1000，遍历查询比用in查询慢
     */
    public static List<String[]> getInIdList(String ids) {
        String[] all = ids.split(",");
        int l = all.length;
        int unit = 1000;
        List<String[]> result = new ArrayList<String[]>();
        for (int i = 0; i < l; ) {
            int j = i + unit;
            if (j > l) {
                j = l;
            }
            int len = j - i;
            String[] arr = new String[len];
            System.arraycopy(all, i, arr, 0, len);
            result.add(arr);
            i = j;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(testPassword("1111111"));
        System.out.println(testPassword("11111111"));
        System.out.println(testPassword("11111a11"));
        System.out.println(testPassword("1b1ee11d11c1"));
        System.out.println(testPassword("111df1111..sd12"));
        System.out.println(testPassword("/./1111111"));


        System.out.println(isNum("0.55"));
        System.out.println(isNum("-0.55"));//true
        System.out.println(isNum("15"));
        System.out.println(isNum("a"));
        System.out.println(isNum("+"));

        System.out.println(StringUtils.isNumeric("0.55"));
        System.out.println(StringUtils.isNumeric("-0.55"));//false
        System.out.println(StringUtils.isNumeric("15"));
        System.out.println(StringUtils.isNumeric("a"));
        System.out.println(StringUtils.isNumeric("+"));
    }
}
