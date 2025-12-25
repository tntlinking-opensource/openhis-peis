package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.AppOrder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 小程序微信订单(AppOrder)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:11:46
 */
public interface AppOrderMapper extends BaseMapper<AppOrder> {

    /**
     * 分页查询[小程序微信订单]列表
     *
     * @param page  分页参数
     * @param param AppOrder查询参数
     * @return 分页数据
     */
    IPage<AppOrder> getPage(PageParam<AppOrder> page, @Param("param") AppOrder param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppOrder getInfoById(@Param("id") String id);

}
