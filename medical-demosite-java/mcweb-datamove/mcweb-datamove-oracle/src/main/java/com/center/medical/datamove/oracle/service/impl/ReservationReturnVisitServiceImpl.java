package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ReservationReturnVisitMapper;
import com.center.medical.datamove.oracle.bean.model.ReservationReturnVisit;
import com.center.medical.datamove.oracle.service.ReservationReturnVisitService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (ReservationReturnVisit)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:20
 */
@Slf4j
@Service("reservationReturnVisitService")
@RequiredArgsConstructor
public class ReservationReturnVisitServiceImpl extends ServiceImpl<ReservationReturnVisitMapper, ReservationReturnVisit> implements ReservationReturnVisitService {

    private final ReservationReturnVisitMapper reservationReturnVisitMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param ReservationReturnVisit查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReservationReturnVisit> getPage(PageParam<ReservationReturnVisit> page, ReservationReturnVisit param) {
        return reservationReturnVisitMapper.getPage(page, param);
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


