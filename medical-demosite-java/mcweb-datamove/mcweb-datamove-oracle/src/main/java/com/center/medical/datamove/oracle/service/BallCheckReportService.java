package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.BallCheckReport;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * TJ团检报告主表(BallCheckReport)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:12:18
 */
public interface BallCheckReportService extends IService<BallCheckReport> {

    /**
     * 分页查询[TJ团检报告主表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<BallCheckReport> getPage(PageParam<BallCheckReport> page, BallCheckReport param);


}

