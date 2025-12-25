package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.QxRoleResource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (QxRoleResource)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:23:50
 */
public interface QxRoleResourceService extends IService<QxRoleResource> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<QxRoleResource> getPage(PageParam<QxRoleResource> page, QxRoleResource param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键roleId
     * @return 详情信息
     */
    QxRoleResource getInfoById(Object id);

}

