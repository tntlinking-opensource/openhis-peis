package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdCompareReport;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 对比报告表，用于存储对报告都有哪些生成过(MdCompareReport)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:17
 */
public interface MdCompareReportMapper extends BaseMapper<MdCompareReport> {

    /**
     * 分页查询[对比报告表，用于存储对报告都有哪些生成过]列表
     *
     * @param page  分页参数
     * @param param MdCompareReport查询参数
     * @return 分页数据
     */
    IPage<MdCompareReport> getPage(PageParam<MdCompareReport> page, @Param("param") MdCompareReport param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCompareReport getInfoById(@Param("id") String id);

}
