package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.InspectReportMapper;
import com.center.medical.bean.model.InspectReport;
import com.center.medical.service.InspectReportService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 检验报告生成记录(InspectReport)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:48
 */
@Slf4j
@Service("inspectReportService")
@RequiredArgsConstructor
public class InspectReportServiceImpl extends ServiceImpl<InspectReportMapper, InspectReport> implements InspectReportService {

    private final InspectReportMapper inspectReportMapper;

    /**
     * 分页查询[检验报告生成记录]列表
     *
     * @param page  分页参数
     * @param param InspectReport查询参数
     * @return 分页数据
     */
    @Override
    public IPage<InspectReport> getList(PageParam<InspectReport> page, InspectReport param) {
        return inspectReportMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public InspectReport getInfoById(String id) {
        return inspectReportMapper.getInfoById(id);
    }

}

