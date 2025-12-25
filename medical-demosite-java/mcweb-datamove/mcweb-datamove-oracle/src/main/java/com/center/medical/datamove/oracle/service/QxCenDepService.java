package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.QxCenDep;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (QxCenDep)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:23:44
 */
public interface QxCenDepService extends IService<QxCenDep> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<QxCenDep> getPage(PageParam<QxCenDep> page, QxCenDep param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    QxCenDep getInfoById(String id);

}

