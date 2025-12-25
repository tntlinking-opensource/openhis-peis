package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.CompareReport;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 对比报告表，用于存储对报告都有哪些生成过(CompareReport)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:23
 */
public interface CompareReportMapper extends BaseMapper<CompareReport> {

    /**
     * 分页查询[对比报告表，用于存储对报告都有哪些生成过]列表
     *
     * @param page  分页参数
     * @param param CompareReport查询参数
     * @return 分页数据
     */
    IPage<CompareReport> getList(PageParam<CompareReport> page, @Param("param") CompareReport param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    CompareReport getInfoById(@Param("id") String id);

}
