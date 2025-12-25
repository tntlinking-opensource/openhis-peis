package com.center.medical.appadmin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.appadmin.bean.model.Appointment;
import com.center.medical.appadmin.bean.param.AppointmentParam;
import com.center.medical.appadmin.dao.AppointmentMapper;
import com.center.medical.appadmin.service.AppointmentService;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 体检预约(Appointment)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-03-27 18:53:56
 */
@Slf4j
@Service("appointmentService")
@RequiredArgsConstructor
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {

    private final AppointmentMapper appointmentMapper;

    /**
     * 分页查询[体检预约]列表
     *
     * @param page  分页参数
     * @param param Appointment查询参数
     * @return 分页数据
     */
    @Override
    @DataSource(value = DataSourceType.APPLET)
    public IPage<Appointment> getPage(PageParam<Appointment> page, AppointmentParam param) {
        return appointmentMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    @DataSource(value = DataSourceType.APPLET)
    public Appointment getInfoById(String id) {
        return appointmentMapper.getInfoById(id);
    }


    /**
     * 保存预约信息
     * @param appointment
     */
    @Override
    @DataSource(value = DataSourceType.APPLET)
    public void saveAppointment(Appointment appointment) {
        save(appointment);
    }
}

