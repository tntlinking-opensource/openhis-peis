package com.center.medical.datamove.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Sellcustomer;
import org.apache.ibatis.annotations.Param;

/**
 * 我的客户表(Sellcustomer)数据库访问层
 *
 * @author ay
 * @since 2023-07-25 21:00:58
 */
public interface OrSellcustomerMapper extends BaseMapper<Sellcustomer> {

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
