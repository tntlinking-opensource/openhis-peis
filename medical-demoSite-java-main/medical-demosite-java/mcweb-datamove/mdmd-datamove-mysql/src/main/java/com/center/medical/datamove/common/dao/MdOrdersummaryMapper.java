package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdOrdersummary;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 订单总结表(MdOrdersummary)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:30
 */
public interface MdOrdersummaryMapper extends BaseMapper<MdOrdersummary> {

    /**
     * 分页查询[订单总结表]列表
     *
     * @param page  分页参数
     * @param param MdOrdersummary查询参数
     * @return 分页数据
     */
    IPage<MdOrdersummary> getPage(PageParam<MdOrdersummary> page, @Param("param") MdOrdersummary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdOrdersummary getInfoById(@Param("id") String id);

}
