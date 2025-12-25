package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.ReportShareMain;
import com.center.medical.bean.param.ReportSharePageParam;
import com.center.medical.bean.vo.ReportShareMainVo;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 报告分享主表(ReportShareMain)数据库访问层
 *
 * @author ay
 * @since 2023-09-19 16:19:53
 */
public interface ReportShareMainMapper extends BaseMapper<ReportShareMain> {

    /**
     * 分页查询[报告分享主表]列表
     *
     * @param page  分页参数
     * @param param ReportShareMain查询参数
     * @return 分页数据
     */
    IPage<ReportShareMainVo> getPage(PageParam<ReportShareMainVo> page, @Param("param") ReportSharePageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReportShareMain getInfoById(@Param("id") String id);

}
