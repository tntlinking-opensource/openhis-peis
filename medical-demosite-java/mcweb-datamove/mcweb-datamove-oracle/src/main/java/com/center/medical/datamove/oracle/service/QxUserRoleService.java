package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.QxUserRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (QxUserRole)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:23:55
 */
public interface QxUserRoleService extends IService<QxUserRole> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<QxUserRole> getPage(PageParam<QxUserRole> page, QxUserRole param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    QxUserRole getInfoById(String id);

}

