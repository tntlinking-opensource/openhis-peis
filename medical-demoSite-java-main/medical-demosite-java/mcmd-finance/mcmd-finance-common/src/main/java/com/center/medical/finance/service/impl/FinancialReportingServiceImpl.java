package com.center.medical.finance.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.FinancialReporting;
import com.center.medical.finance.bean.param.FinancialReportingParam;
import com.center.medical.finance.dao.FinancialReportingMapper;
import com.center.medical.finance.service.FinancialReportingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 财务提报(FinancialReporting)表服务实现类
 *
 * @author ay
 * @since 2023-06-27 16:01:56
 */
@Slf4j
@Service("financialReportingService")
@RequiredArgsConstructor
public class FinancialReportingServiceImpl extends ServiceImpl<FinancialReportingMapper, FinancialReporting> implements FinancialReportingService {

    private final FinancialReportingMapper financialReportingMapper;

    /**
    * 分页查询[财务提报]列表
    *
    * @param page 分页参数
    * @param param FinancialReporting查询参数
    * @return 分页数据
    */
    @Override
    public IPage<FinancialReporting> getList(PageParam<FinancialReporting> page, FinancialReportingParam param) {
        return financialReportingMapper.getList(page, param);
    }

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    @Override
    public FinancialReporting getInfoById(String id){
        return financialReportingMapper.getInfoById(id);
    };

}

