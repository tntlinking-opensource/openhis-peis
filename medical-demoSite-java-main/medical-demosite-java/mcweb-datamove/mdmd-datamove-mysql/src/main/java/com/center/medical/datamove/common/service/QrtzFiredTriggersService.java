package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.QrtzFiredTriggers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 已触发的触发器表(QrtzFiredTriggers)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:30
 */
public interface QrtzFiredTriggersService extends IService<QrtzFiredTriggers> {

    /**
     * 分页查询[已触发的触发器表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<QrtzFiredTriggers> getPage(PageParam<QrtzFiredTriggers> page, QrtzFiredTriggers param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    QrtzFiredTriggers getInfoById(String id);

}

