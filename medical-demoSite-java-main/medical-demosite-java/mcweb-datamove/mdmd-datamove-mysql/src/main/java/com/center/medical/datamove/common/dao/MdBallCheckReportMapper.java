package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdBallCheckReport;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * TJ团检报告主表(MdBallCheckReport)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:07
 */
public interface MdBallCheckReportMapper extends BaseMapper<MdBallCheckReport> {

    /**
     * 分页查询[TJ团检报告主表]列表
     *
     * @param page  分页参数
     * @param param MdBallCheckReport查询参数
     * @return 分页数据
     */
    IPage<MdBallCheckReport> getPage(PageParam<MdBallCheckReport> page, @Param("param") MdBallCheckReport param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBallCheckReport getInfoById(@Param("id") String id);

}
