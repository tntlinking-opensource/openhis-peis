package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.QrtzTriggers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 触发器详细信息表(QrtzTriggers)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:33
 */
public interface QrtzTriggersService extends IService<QrtzTriggers> {

    /**
     * 分页查询[触发器详细信息表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<QrtzTriggers> getPage(PageParam<QrtzTriggers> page, QrtzTriggers param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    QrtzTriggers getInfoById(String id);

}

