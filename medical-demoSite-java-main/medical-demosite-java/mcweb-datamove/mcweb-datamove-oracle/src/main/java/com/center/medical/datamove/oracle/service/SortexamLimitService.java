package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.SortexamLimit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 预约管理(SortexamLimit)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:25:03
 */
public interface SortexamLimitService extends IService<SortexamLimit> {

    /**
     * 分页查询[预约管理]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SortexamLimit> getPage(PageParam<SortexamLimit> page, SortexamLimit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SortexamLimit getInfoById(String id);

}

