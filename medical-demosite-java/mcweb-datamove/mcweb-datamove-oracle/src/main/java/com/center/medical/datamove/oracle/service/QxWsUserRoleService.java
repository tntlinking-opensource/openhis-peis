package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.QxWsUserRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (QxWsUserRole)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:09
 */
public interface QxWsUserRoleService extends IService<QxWsUserRole> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<QxWsUserRole> getPage(PageParam<QxWsUserRole> page, QxWsUserRole param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    QxWsUserRole getInfoById(String id);

}

