package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Reservation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 预约管理(Reservation)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:18
 */
public interface ReservationMapper extends BaseMapper<Reservation> {

    /**
     * 分页查询[预约管理]列表
     *
     * @param page  分页参数
     * @param param Reservation查询参数
     * @return 分页数据
     */
    IPage<Reservation> getPage(PageParam<Reservation> page, @Param("param") Reservation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Reservation getInfoById(@Param("id") String id);

}
