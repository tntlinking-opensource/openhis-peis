package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrSellcustomer;
import org.apache.ibatis.annotations.Param;

/**
 * 我的客户表(Sellcustomer)数据库访问层
 *
 * @author ay
 * @since 2023-07-25 21:00:58
 */
public interface OrSellcustomerMapper extends BaseMapper<OrSellcustomer> {

    /**
     * 分页查询[我的客户表]列表
     *
     * @param page  分页参数
     * @param param Sellcustomer查询参数
     * @return 分页数据
     */
    IPage<OrSellcustomer> getPage(PageParam<OrSellcustomer> page, @Param("param") OrSellcustomer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrSellcustomer getInfoById(@Param("id") String id);

}
