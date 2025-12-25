package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.QxWsRoleResource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (QxWsRoleResource)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:06
 */
public interface QxWsRoleResourceService extends IService<QxWsRoleResource> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<QxWsRoleResource> getPage(PageParam<QxWsRoleResource> page, QxWsRoleResource param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键roleId
     * @return 详情信息
     */
    QxWsRoleResource getInfoById(Object id);

}

