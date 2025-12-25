package com.center.medical.report.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.ReportShareTwo;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.dao.ReportShareTwoMapper;
import com.center.medical.report.service.ReportShareTwoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 报告分享和报告关系表(ReportShareTwo)服务实现类
 *
 * @author ay
 * @since 2023-09-19 16:23:51
 */
@Slf4j
@Service("reportShareTwoService")
@RequiredArgsConstructor
public class ReportShareTwoServiceImpl extends ServiceImpl<ReportShareTwoMapper, ReportShareTwo> implements ReportShareTwoService {

    private final ReportShareTwoMapper reportShareTwoMapper;

    /**
     * 分页查询[报告分享和报告关系表]列表
     *
     * @param page  分页参数
     * @param param ReportShareTwo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReportShareTwo> getPage(PageParam<ReportShareTwo> page, ReportShareTwo param) {
        return reportShareTwoMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ReportShareTwo getInfoById(String id) {
        return reportShareTwoMapper.getInfoById(id);
    }

}

