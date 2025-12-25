/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.bean;

import cn.hutool.core.collection.CollectionUtil;
import com.center.medical.app.bean.bo.SmsInfoBo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yami
 */
public class SmsInfoContext {

    /**
     * The request holder.
     */
    private static ThreadLocal<List<SmsInfoBo>> smsInfoHolder = new ThreadLocal<List<SmsInfoBo>>();


    public static List<SmsInfoBo> get() {

        List<SmsInfoBo> list = smsInfoHolder.get();
        if (CollectionUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        return smsInfoHolder.get();
    }

    public static void set(List<SmsInfoBo> smsInfoBos) {
        smsInfoHolder.set(smsInfoBos);
    }

    public static void put(SmsInfoBo smsInfoBo) {
        List<SmsInfoBo> smsInfoBos = smsInfoHolder.get();
        if (CollectionUtil.isEmpty(smsInfoBos)) {
            smsInfoBos = new ArrayList<>();
        }
        smsInfoBos.add(smsInfoBo);
        smsInfoHolder.set(smsInfoBos);
    }

    public static void clean() {
        if (smsInfoHolder.get() != null) {
            smsInfoHolder.remove();
        }
    }
}
