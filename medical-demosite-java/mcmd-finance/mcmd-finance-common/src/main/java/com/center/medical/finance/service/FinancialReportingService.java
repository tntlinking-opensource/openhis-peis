package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.FinancialReporting;
import com.center.medical.finance.bean.param.FinancialReportingParam;

/**
 * 财务提报(FinancialReporting)表服务接口
 *
 * @author ay
 * @since 2023-06-27 16:01:56
 */
public interface FinancialReportingService extends IService<FinancialReporting> {

    /**
    * 分页查询[财务提报]列表
    *
    * @param page 分页参数
    * @param param 查询参数
    * @return 分页数据
    */
    IPage<FinancialReporting> getList(PageParam<FinancialReporting> page, FinancialReportingParam param);

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    FinancialReporting getInfoById(String id);

}

