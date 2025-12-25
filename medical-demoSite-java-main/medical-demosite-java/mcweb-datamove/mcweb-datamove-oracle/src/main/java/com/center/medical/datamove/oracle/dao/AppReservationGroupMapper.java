package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.AppReservationGroup;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 小程序团检预约人数(AppReservationGroup)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:11:54
 */
public interface AppReservationGroupMapper extends BaseMapper<AppReservationGroup> {

    /**
     * 分页查询[小程序团检预约人数]列表
     *
     * @param page  分页参数
     * @param param AppReservationGroup查询参数
     * @return 分页数据
     */
    IPage<AppReservationGroup> getPage(PageParam<AppReservationGroup> page, @Param("param") AppReservationGroup param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppReservationGroup getInfoById(@Param("id") String id);

}
