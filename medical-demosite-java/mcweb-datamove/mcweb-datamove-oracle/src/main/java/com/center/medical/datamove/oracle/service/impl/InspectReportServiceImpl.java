package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.InspectReportMapper;
import com.center.medical.datamove.oracle.bean.model.InspectReport;
import com.center.medical.datamove.oracle.service.InspectReportService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (InspectReport)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:16
 */
@Slf4j
@Service("inspectReportService")
@RequiredArgsConstructor
public class InspectReportServiceImpl extends ServiceImpl<InspectReportMapper, InspectReport> implements InspectReportService {

    private final InspectReportMapper inspectReportMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param InspectReport查询参数
     * @return 分页数据
     */
    @Override
    public IPage<InspectReport> getPage(PageParam<InspectReport> page, InspectReport param) {
        return inspectReportMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public InspectReport getInfoById(String id) {
        return inspectReportMapper.getInfoById(id);
    }

    ;

}


