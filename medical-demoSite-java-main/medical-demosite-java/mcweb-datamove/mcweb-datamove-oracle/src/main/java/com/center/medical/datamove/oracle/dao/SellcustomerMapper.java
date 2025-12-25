package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Sellcustomer;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 我的客户表(Sellcustomer)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:51
 */
public interface SellcustomerMapper extends BaseMapper<Sellcustomer> {

    /**
     * 分页查询[我的客户表]列表
     *
     * @param page  分页参数
     * @param param Sellcustomer查询参数
     * @return 分页数据
     */
    IPage<Sellcustomer> getPage(PageParam<Sellcustomer> page, @Param("param") Sellcustomer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Sellcustomer getInfoById(@Param("id") String id);

}
