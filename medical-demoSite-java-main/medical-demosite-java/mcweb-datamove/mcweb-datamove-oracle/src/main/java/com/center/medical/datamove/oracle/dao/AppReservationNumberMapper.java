package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.AppReservationNumber;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 小程序预约数量上限(AppReservationNumber)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:11:57
 */
public interface AppReservationNumberMapper extends BaseMapper<AppReservationNumber> {

    /**
     * 分页查询[小程序预约数量上限]列表
     *
     * @param page  分页参数
     * @param param AppReservationNumber查询参数
     * @return 分页数据
     */
    IPage<AppReservationNumber> getPage(PageParam<AppReservationNumber> page, @Param("param") AppReservationNumber param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppReservationNumber getInfoById(@Param("id") String id);

}
