package com.center.medical.reservation.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.model.ReservationGroupTime;
import com.center.medical.reservation.dao.ReservationGroupTimeMapper;
import com.center.medical.reservation.service.ReservationGroupTimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 团体预约和预约设置关联表(ReservationGroupTime)服务实现类
 *
 * @author ay
 * @since 2024-04-26 13:58:01
 */
@Slf4j
@Service("reservationGroupTimeService")
@RequiredArgsConstructor
public class ReservationGroupTimeServiceImpl extends ServiceImpl<ReservationGroupTimeMapper, ReservationGroupTime> implements ReservationGroupTimeService {

    private final ReservationGroupTimeMapper reservationGroupTimeMapper;

    /**
     * 分页查询[团体预约和预约设置关联表]列表
     *
     * @param page  分页参数
     * @param param ReservationGroupTime查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReservationGroupTime> getPage(PageParam<ReservationGroupTime> page, ReservationGroupTime param) {
        return reservationGroupTimeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ReservationGroupTime getInfoById(String id) {
        return reservationGroupTimeMapper.getInfoById(id);
    }

}

