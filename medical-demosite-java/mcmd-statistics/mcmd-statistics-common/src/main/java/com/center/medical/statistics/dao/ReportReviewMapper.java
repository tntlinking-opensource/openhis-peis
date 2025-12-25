package com.center.medical.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.ReportContent;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.ReportReviewParam;
import com.center.medical.statistics.bean.vo.ReportReviewVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 生成报告内容(ReportContent)数据库访问层
 *
 * @author ay
 * @since 2023-08-14 14:54:49
 */
public interface ReportReviewMapper extends BaseMapper<ReportContent> {

    /**
     * 分页查询[生成报告内容]列表
     *
     * @param page  分页参数
     * @param param ReportContent查询参数
     * @return 分页数据
     */
    IPage<ReportReviewVo> getPage(PageParam<ReportReviewVo> page, @Param("param") ReportReviewParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReportContent getInfoById(@Param("id") String id);

    /**
     * 获取合计数据
     * @param page
     * @param param
     * @return
     */
    IPage<ReportReviewVo> getTotalData(PageParam<ReportReviewVo> page,@Param("param") ReportReviewParam param);

    /**
     * 导出报告审核工作量
     * @param param
     * @return
     */
    List<ReportReviewVo> exportData(@Param("param") ReportReviewParam param);


    /**
     * 分页查询-一审
     * @param page
     * @param param
     * @return
     */
    IPage<ReportReviewVo> getFistListData(PageParam<ReportReviewVo> page , @Param("param") ReportReviewParam param);


    /**
     * 获取合计数据-一审
     * @param page
     * @param param
     * @return
     */
    IPage<ReportReviewVo> getFistTotalData(PageParam<ReportReviewVo> page, @Param("param") ReportReviewParam param);


    /**
     * 导出-一审
     * @param param
     * @return
     */
    List<ReportReviewVo> exportFistData( @Param("param") ReportReviewParam param);

    /**
     * 分页查询-二审
     * @param page
     * @param param
     * @return
     */
    IPage<ReportReviewVo> getSecondListData(PageParam<ReportReviewVo> page , @Param("param") ReportReviewParam param);


    /**
     * 获取合计数据-二审
     * @param page
     * @param param
     * @return
     */
    IPage<ReportReviewVo> getSecondTotalData(PageParam<ReportReviewVo> page , @Param("param") ReportReviewParam param);

    /**
     * 导出-二审
     * @param param
     * @return
     */
    List<ReportReviewVo> exportSecond(@Param("param") ReportReviewParam param);
}
