package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdSellcustomer;
import org.apache.ibatis.annotations.Param;

/**
 * 我的客户表(CrmSellcustomer)数据库访问层
 *
 * @author ay
 * @since 2023-07-25 21:02:11
 */
public interface MdSellcustomerMapper extends BaseMapper<MdSellcustomer> {

    /**
     * 分页查询[我的客户表]列表
     *
     * @param page  分页参数
     * @param param CrmSellcustomer查询参数
     * @return 分页数据
     */
    IPage<MdSellcustomer> getPage(PageParam<MdSellcustomer> page, @Param("param") MdSellcustomer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSellcustomer getInfoById(@Param("id") String id);

}
