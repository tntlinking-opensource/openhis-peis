package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdOrdersummary;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 订单总结表(MdOrdersummary)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:30
 */
public interface MdOrdersummaryService extends IService<MdOrdersummary> {

    /**
     * 分页查询[订单总结表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdOrdersummary> getPage(PageParam<MdOrdersummary> page, MdOrdersummary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdOrdersummary getInfoById(String id);

}

