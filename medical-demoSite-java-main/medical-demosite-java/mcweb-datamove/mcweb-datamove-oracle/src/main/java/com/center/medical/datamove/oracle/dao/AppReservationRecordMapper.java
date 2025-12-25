package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.AppReservationRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 小程序预约记录（团检+个检）(AppReservationRecord)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:11:59
 */
public interface AppReservationRecordMapper extends BaseMapper<AppReservationRecord> {

    /**
     * 分页查询[小程序预约记录（团检+个检）]列表
     *
     * @param page  分页参数
     * @param param AppReservationRecord查询参数
     * @return 分页数据
     */
    IPage<AppReservationRecord> getPage(PageParam<AppReservationRecord> page, @Param("param") AppReservationRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppReservationRecord getInfoById(@Param("id") String id);

}
