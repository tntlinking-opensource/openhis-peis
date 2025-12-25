package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdReservationReturnVisit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 预约回访记录(MdReservationReturnVisit)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:18
 */
public interface MdReservationReturnVisitMapper extends BaseMapper<MdReservationReturnVisit> {

    /**
     * 分页查询[预约回访记录]列表
     *
     * @param page  分页参数
     * @param param MdReservationReturnVisit查询参数
     * @return 分页数据
     */
    IPage<MdReservationReturnVisit> getPage(PageParam<MdReservationReturnVisit> page, @Param("param") MdReservationReturnVisit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdReservationReturnVisit getInfoById(@Param("id") String id);

}
