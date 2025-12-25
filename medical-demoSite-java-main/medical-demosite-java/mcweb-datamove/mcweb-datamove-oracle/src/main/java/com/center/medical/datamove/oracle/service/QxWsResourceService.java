package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.QxWsResource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (QxWsResource)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:04
 */
public interface QxWsResourceService extends IService<QxWsResource> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<QxWsResource> getPage(PageParam<QxWsResource> page, QxWsResource param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    QxWsResource getInfoById(String id);

}

