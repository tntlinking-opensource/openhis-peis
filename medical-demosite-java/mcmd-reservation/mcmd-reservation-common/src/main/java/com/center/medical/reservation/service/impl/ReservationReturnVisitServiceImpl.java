package com.center.medical.reservation.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.model.ReservationReturnVisit;
import com.center.medical.reservation.dao.ReservationReturnVisitMapper;
import com.center.medical.reservation.service.ReservationReturnVisitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 预约回访记录(ReservationReturnVisit)表服务实现类
 *
 * @author ay
 * @since 2023-03-18 08:54:15
 */
@Slf4j
@Service("reservationReturnVisitService")
@RequiredArgsConstructor
public class ReservationReturnVisitServiceImpl extends ServiceImpl<ReservationReturnVisitMapper, ReservationReturnVisit> implements ReservationReturnVisitService {

    private final ReservationReturnVisitMapper reservationReturnVisitMapper;

    /**
     * 分页查询[预约回访记录]列表
     *
     * @param page  分页参数
     * @param param ReservationReturnVisit查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReservationReturnVisit> getList(PageParam<ReservationReturnVisit> page, ReservationReturnVisit param) {
        return reservationReturnVisitMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ReservationReturnVisit getInfoById(String id) {
        return reservationReturnVisitMapper.getInfoById(id);
    }

}

