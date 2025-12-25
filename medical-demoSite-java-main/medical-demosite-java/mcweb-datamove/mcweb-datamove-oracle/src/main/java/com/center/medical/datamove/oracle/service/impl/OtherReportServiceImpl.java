package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.OtherReportMapper;
import com.center.medical.datamove.oracle.bean.model.OtherReport;
import com.center.medical.datamove.oracle.service.OtherReportService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (OtherReport)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:28
 */
@Slf4j
@Service("otherReportService")
@RequiredArgsConstructor
public class OtherReportServiceImpl extends ServiceImpl<OtherReportMapper, OtherReport> implements OtherReportService {

    private final OtherReportMapper otherReportMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param OtherReport查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OtherReport> getPage(PageParam<OtherReport> page, OtherReport param) {
        return otherReportMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OtherReport getInfoById(String id) {
        return otherReportMapper.getInfoById(id);
    }

}


