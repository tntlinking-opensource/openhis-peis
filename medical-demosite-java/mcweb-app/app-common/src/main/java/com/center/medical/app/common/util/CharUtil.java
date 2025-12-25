/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.common.util;

/**
 * 获取带有中文字符的长度
 *
 * @author yami
 */
public class CharUtil {
    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     *
     * @param s 需要得到长度的字符串
     * @return int 得到的字符串长度
     */
    public static int length(String s) {
        if (s == null) {
            return 0;
        }
        int len = 0;
        for (char item : s.toCharArray()) {
            len++;
            if (item / 0x80 == 0) {
                continue;
            }
            len++;
        }
        return len;
    }


}
