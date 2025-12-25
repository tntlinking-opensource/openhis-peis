package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppReservationGroupTimeMapper;
import com.center.medical.datamove.oracle.bean.model.AppReservationGroupTime;
import com.center.medical.datamove.oracle.service.AppReservationGroupTimeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 小程序团检预约各时间段人数(AppReservationGroupTime)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:57
 */
@Slf4j
@Service("appReservationGroupTimeService")
@RequiredArgsConstructor
public class AppReservationGroupTimeServiceImpl extends ServiceImpl<AppReservationGroupTimeMapper, AppReservationGroupTime> implements AppReservationGroupTimeService {

    private final AppReservationGroupTimeMapper appReservationGroupTimeMapper;

    /**
     * 分页查询[小程序团检预约各时间段人数]列表
     *
     * @param page  分页参数
     * @param param AppReservationGroupTime查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppReservationGroupTime> getPage(PageParam<AppReservationGroupTime> page, AppReservationGroupTime param) {
        return appReservationGroupTimeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppReservationGroupTime getInfoById(String id) {
        return appReservationGroupTimeMapper.getInfoById(id);
    }

}


