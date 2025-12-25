package com.center.medical.appadmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.appadmin.bean.model.Appointment;
import com.center.medical.appadmin.bean.param.AppointmentParam;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检预约(Appointment)表服务接口
 *
 * @author 路飞船长
 * @since 2023-03-27 18:53:56
 */
public interface AppointmentService extends IService<Appointment> {

    /**
     * 分页查询[体检预约]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Appointment> getPage(PageParam<Appointment> page, AppointmentParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Appointment getInfoById(String id);

    /**
     * 保存预约信息
     * @param appointment
     */
    @DataSource(value = DataSourceType.APPLET)
    void saveAppointment(Appointment appointment);
}

