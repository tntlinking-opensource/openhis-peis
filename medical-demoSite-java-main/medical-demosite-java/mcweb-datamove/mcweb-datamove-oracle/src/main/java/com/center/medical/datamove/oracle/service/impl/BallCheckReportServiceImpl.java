package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BallCheckReportMapper;
import com.center.medical.datamove.oracle.bean.model.BallCheckReport;
import com.center.medical.datamove.oracle.service.BallCheckReportService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * TJ团检报告主表(BallCheckReport)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:18
 */
@Slf4j
@Service("ballCheckReportService")
@RequiredArgsConstructor
public class BallCheckReportServiceImpl extends ServiceImpl<BallCheckReportMapper, BallCheckReport> implements BallCheckReportService {

    private final BallCheckReportMapper ballCheckReportMapper;

    /**
     * 分页查询[TJ团检报告主表]列表
     *
     * @param page  分页参数
     * @param param BallCheckReport查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BallCheckReport> getPage(PageParam<BallCheckReport> page, BallCheckReport param) {
        return ballCheckReportMapper.getPage(page, param);
    }


}


