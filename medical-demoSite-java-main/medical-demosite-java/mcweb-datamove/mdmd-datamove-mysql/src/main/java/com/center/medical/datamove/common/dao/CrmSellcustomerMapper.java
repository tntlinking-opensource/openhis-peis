package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.CrmSellcustomer;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 我的客户表(CrmSellcustomer)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:01
 */
public interface CrmSellcustomerMapper extends BaseMapper<CrmSellcustomer> {

    /**
     * 分页查询[我的客户表]列表
     *
     * @param page  分页参数
     * @param param CrmSellcustomer查询参数
     * @return 分页数据
     */
    IPage<CrmSellcustomer> getPage(PageParam<CrmSellcustomer> page, @Param("param") CrmSellcustomer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmSellcustomer getInfoById(@Param("id") String id);

}
