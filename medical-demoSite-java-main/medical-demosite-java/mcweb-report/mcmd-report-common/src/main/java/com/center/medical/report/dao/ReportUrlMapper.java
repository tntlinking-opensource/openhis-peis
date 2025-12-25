package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.ReportUrl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BG科室报告目录表(ReportUrl)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-23 17:11:14
 */
public interface ReportUrlMapper extends BaseMapper<ReportUrl> {

    /**
     * 分页查询[BG科室报告目录表]列表
     *
     * @param page  分页参数
     * @param param ReportUrl查询参数
     * @return 分页数据
     */
    IPage<ReportUrl> getPage(PageParam<ReportUrl> page, @Param("param") ReportUrl param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReportUrl getInfoById(@Param("id") String id);

    /**
     * 根据体检号获取记录详情
     *
     * @param patientcode 体检号
     * @return 详情信息
     */
    List<ReportUrl> getList(@Param("patientcode")String patientcode, @Param("isHead")int isHead, @Param("diseaseHealth")int diseaseHealth);
}
