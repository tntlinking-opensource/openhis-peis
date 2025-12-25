package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.CompareReportMapper;
import com.center.medical.datamove.oracle.bean.model.CompareReport;
import com.center.medical.datamove.oracle.service.CompareReportService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 对比报告表，用于存储对报告都有哪些生成过(CompareReport)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:17:46
 */
@Slf4j
@Service("compareReportService")
@RequiredArgsConstructor
public class CompareReportServiceImpl extends ServiceImpl<CompareReportMapper, CompareReport> implements CompareReportService {

    private final CompareReportMapper compareReportMapper;

    /**
     * 分页查询[对比报告表，用于存储对报告都有哪些生成过]列表
     *
     * @param page  分页参数
     * @param param CompareReport查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CompareReport> getPage(PageParam<CompareReport> page, CompareReport param) {
        return compareReportMapper.getPage(page, param);
    }


}


