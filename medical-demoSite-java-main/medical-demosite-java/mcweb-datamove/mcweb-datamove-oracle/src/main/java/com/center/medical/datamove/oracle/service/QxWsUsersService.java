package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.QxWsUsers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (QxWsUsers)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:10
 */
public interface QxWsUsersService extends IService<QxWsUsers> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<QxWsUsers> getPage(PageParam<QxWsUsers> page, QxWsUsers param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    QxWsUsers getInfoById(String id);

}

