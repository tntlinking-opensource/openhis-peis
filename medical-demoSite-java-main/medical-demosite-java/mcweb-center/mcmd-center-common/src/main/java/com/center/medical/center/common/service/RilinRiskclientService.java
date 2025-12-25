package com.center.medical.center.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.sellcrm.bean.model.Riskclient;

/**
 * 对接瑞林萨尔健康管理系统,重大阳性
 * @since 2025-03-25 08:29:24
 */
public interface RilinRiskclientService extends IService<Riskclient> {

    /**
     * 实时上传，重大阳性
     */
    void sync();

}
