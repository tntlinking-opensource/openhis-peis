package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdFinancialReportingMapper;
import com.center.medical.datamove.common.bean.model.MdFinancialReporting;
import com.center.medical.datamove.common.service.MdFinancialReportingService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 财务提报(MdFinancialReporting)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:18
 */
@Slf4j
@Service("mdFinancialReportingService")
@RequiredArgsConstructor
public class MdFinancialReportingServiceImpl extends ServiceImpl<MdFinancialReportingMapper, MdFinancialReporting> implements MdFinancialReportingService {

    private final MdFinancialReportingMapper mdFinancialReportingMapper;

    /**
     * 分页查询[财务提报]列表
     *
     * @param page  分页参数
     * @param param MdFinancialReporting查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdFinancialReporting> getPage(PageParam<MdFinancialReporting> page, MdFinancialReporting param) {
        return mdFinancialReportingMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdFinancialReporting getInfoById(String id) {
        return mdFinancialReportingMapper.getInfoById(id);
    }

}


