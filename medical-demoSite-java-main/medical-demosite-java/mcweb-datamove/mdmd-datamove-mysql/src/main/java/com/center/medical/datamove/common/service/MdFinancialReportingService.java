package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdFinancialReporting;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 财务提报(MdFinancialReporting)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:18
 */
public interface MdFinancialReportingService extends IService<MdFinancialReporting> {

    /**
     * 分页查询[财务提报]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdFinancialReporting> getPage(PageParam<MdFinancialReporting> page, MdFinancialReporting param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFinancialReporting getInfoById(String id);

}

