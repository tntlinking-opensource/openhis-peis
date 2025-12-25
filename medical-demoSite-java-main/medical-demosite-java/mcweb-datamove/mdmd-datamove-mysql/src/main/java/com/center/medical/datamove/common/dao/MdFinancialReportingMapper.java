package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdFinancialReporting;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 财务提报(MdFinancialReporting)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:17
 */
public interface MdFinancialReportingMapper extends BaseMapper<MdFinancialReporting> {

    /**
     * 分页查询[财务提报]列表
     *
     * @param page  分页参数
     * @param param MdFinancialReporting查询参数
     * @return 分页数据
     */
    IPage<MdFinancialReporting> getPage(PageParam<MdFinancialReporting> page, @Param("param") MdFinancialReporting param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFinancialReporting getInfoById(@Param("id") String id);

}
