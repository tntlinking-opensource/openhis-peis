package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.QrtzSimpropTriggers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 同步机制的行锁表(QrtzSimpropTriggers)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:32
 */
public interface QrtzSimpropTriggersService extends IService<QrtzSimpropTriggers> {

    /**
     * 分页查询[同步机制的行锁表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<QrtzSimpropTriggers> getPage(PageParam<QrtzSimpropTriggers> page, QrtzSimpropTriggers param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    QrtzSimpropTriggers getInfoById(String id);

}

