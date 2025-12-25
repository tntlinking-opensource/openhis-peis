package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.BallCheckReport;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * TJ团检报告主表(BallCheckReport)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:12:18
 */
public interface BallCheckReportMapper extends BaseMapper<BallCheckReport> {

    /**
     * 分页查询[TJ团检报告主表]列表
     *
     * @param page  分页参数
     * @param param BallCheckReport查询参数
     * @return 分页数据
     */
    IPage<BallCheckReport> getPage(PageParam<BallCheckReport> page, @Param("param") BallCheckReport param);


}
