package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.AppOrderItem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 小程序微信订单项目(AppOrderItem)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:11:48
 */
public interface AppOrderItemMapper extends BaseMapper<AppOrderItem> {

    /**
     * 分页查询[小程序微信订单项目]列表
     *
     * @param page  分页参数
     * @param param AppOrderItem查询参数
     * @return 分页数据
     */
    IPage<AppOrderItem> getPage(PageParam<AppOrderItem> page, @Param("param") AppOrderItem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppOrderItem getInfoById(@Param("id") String id);

}
