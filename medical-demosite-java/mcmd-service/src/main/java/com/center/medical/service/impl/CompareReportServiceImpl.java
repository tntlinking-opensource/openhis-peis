package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.CompareReportMapper;
import com.center.medical.bean.model.CompareReport;
import com.center.medical.service.CompareReportService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 对比报告表，用于存储对报告都有哪些生成过(CompareReport)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:23
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
    public IPage<CompareReport> getList(PageParam<CompareReport> page, CompareReport param) {
        return compareReportMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public CompareReport getInfoById(String id) {
        return compareReportMapper.getInfoById(id);
    }

    ;

}

