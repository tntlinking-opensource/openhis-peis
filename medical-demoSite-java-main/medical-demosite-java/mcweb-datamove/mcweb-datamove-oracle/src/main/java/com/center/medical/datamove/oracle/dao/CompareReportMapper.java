package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.CompareReport;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 对比报告表，用于存储对报告都有哪些生成过(CompareReport)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:17:45
 */
public interface CompareReportMapper extends BaseMapper<CompareReport> {

    /**
     * 分页查询[对比报告表，用于存储对报告都有哪些生成过]列表
     *
     * @param page  分页参数
     * @param param CompareReport查询参数
     * @return 分页数据
     */
    IPage<CompareReport> getPage(PageParam<CompareReport> page, @Param("param") CompareReport param);


}
