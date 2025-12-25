package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.dto.ISDataDto;
import com.center.medical.finance.bean.model.FinancialReporting;
import com.center.medical.finance.bean.param.FinancialReportingParam;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 财务提报(FinancialReporting)表数据库访问层
 *
 * @author ay
 * @since 2023-06-27 16:01:56
 */
public interface FinancialReportingMapper extends BaseMapper<FinancialReporting> {

    /**
    * 分页查询[财务提报]列表
    *
    * @param page 分页参数
    * @param param FinancialReporting查询参数
    * @return 分页数据
    */
    IPage<FinancialReporting> getList(PageParam<FinancialReporting> page, @Param("param") FinancialReportingParam param);

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    FinancialReporting getInfoById(@Param("id") String id);

    /**
     * 查询最新的各分中心的记录
     * @return
     */
    List<ISDataDto> findList(@Param("field") String field,@Param("dateTime") Date dateTime);
}
