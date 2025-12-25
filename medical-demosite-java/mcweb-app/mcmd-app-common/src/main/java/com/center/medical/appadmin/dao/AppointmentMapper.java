package com.center.medical.appadmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.appadmin.bean.model.Appointment;
import com.center.medical.appadmin.bean.param.AppointmentParam;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检预约(Appointment)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-03-27 18:53:56
 */
public interface AppointmentMapper extends BaseMapper<Appointment> {

    /**
     * 分页查询[体检预约]列表
     *
     * @param page  分页参数
     * @param param Appointment查询参数
     * @return 分页数据
     */
    IPage<Appointment> getPage(PageParam<Appointment> page, @Param("param") AppointmentParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Appointment getInfoById(@Param("id") String id);

}
