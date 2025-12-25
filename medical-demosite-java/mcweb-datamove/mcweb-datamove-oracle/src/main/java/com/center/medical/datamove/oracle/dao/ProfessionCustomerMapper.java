package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.ProfessionCustomer;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (ProfessionCustomer)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:39
 */
public interface ProfessionCustomerMapper extends BaseMapper<ProfessionCustomer> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param ProfessionCustomer查询参数
     * @return 分页数据
     */
    IPage<ProfessionCustomer> getPage(PageParam<ProfessionCustomer> page, @Param("param") ProfessionCustomer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ProfessionCustomer getInfoById(@Param("id") String id);

}
