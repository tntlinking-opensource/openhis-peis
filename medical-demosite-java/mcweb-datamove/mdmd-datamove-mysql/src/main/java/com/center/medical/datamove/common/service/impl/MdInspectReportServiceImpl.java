package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdInspectReportMapper;
import com.center.medical.datamove.common.bean.model.MdInspectReport;
import com.center.medical.datamove.common.service.MdInspectReportService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 检验报告生成记录(MdInspectReport)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:22
 */
@Slf4j
@Service("mdInspectReportService")
@RequiredArgsConstructor
public class MdInspectReportServiceImpl extends ServiceImpl<MdInspectReportMapper, MdInspectReport> implements MdInspectReportService {

    private final MdInspectReportMapper mdInspectReportMapper;

    /**
     * 分页查询[检验报告生成记录]列表
     *
     * @param page  分页参数
     * @param param MdInspectReport查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdInspectReport> getPage(PageParam<MdInspectReport> page, MdInspectReport param) {
        return mdInspectReportMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdInspectReport getInfoById(String id) {
        return mdInspectReportMapper.getInfoById(id);
    }

}


