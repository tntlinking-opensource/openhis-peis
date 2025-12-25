package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ReportMapper;
import com.center.medical.datamove.oracle.bean.model.Report;
import com.center.medical.datamove.oracle.service.ReportService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * BG报告主表(Report)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:16
 */
@Slf4j
@Service("reportService")
@RequiredArgsConstructor
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {

    private final ReportMapper reportMapper;

    /**
     * 分页查询[BG报告主表]列表
     *
     * @param page  分页参数
     * @param param Report查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Report> getPage(PageParam<Report> page, Report param) {
        return reportMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Report getInfoById(String id) {
        return reportMapper.getInfoById(id);
    }

}


