package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.QrtzSimpleTriggers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 简单触发器的信息表(QrtzSimpleTriggers)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:32
 */
public interface QrtzSimpleTriggersService extends IService<QrtzSimpleTriggers> {

    /**
     * 分页查询[简单触发器的信息表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<QrtzSimpleTriggers> getPage(PageParam<QrtzSimpleTriggers> page, QrtzSimpleTriggers param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    QrtzSimpleTriggers getInfoById(String id);

}

