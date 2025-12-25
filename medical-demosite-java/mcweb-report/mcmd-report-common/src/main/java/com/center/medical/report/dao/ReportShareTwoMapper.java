package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.ReportShareTwo;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 报告分享和报告关系表(ReportShareTwo)数据库访问层
 *
 * @author ay
 * @since 2023-09-19 16:23:51
 */
public interface ReportShareTwoMapper extends BaseMapper<ReportShareTwo> {

    /**
     * 分页查询[报告分享和报告关系表]列表
     *
     * @param page  分页参数
     * @param param ReportShareTwo查询参数
     * @return 分页数据
     */
    IPage<ReportShareTwo> getPage(PageParam<ReportShareTwo> page, @Param("param") ReportShareTwo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReportShareTwo getInfoById(@Param("id") String id);

    /**
     * 分享报告统计-详情
     * @param page
     * @param id
     * @return
     */
    IPage<ReportShareTwo> details(PageParam<ReportShareTwo> page, @Param("id") String id);
}
