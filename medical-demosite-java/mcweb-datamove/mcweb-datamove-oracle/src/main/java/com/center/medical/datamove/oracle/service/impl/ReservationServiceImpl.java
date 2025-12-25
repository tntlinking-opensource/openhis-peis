package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ReservationMapper;
import com.center.medical.datamove.oracle.bean.model.Reservation;
import com.center.medical.datamove.oracle.service.ReservationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 预约管理(Reservation)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:19
 */
@Slf4j
@Service("reservationService")
@RequiredArgsConstructor
public class ReservationServiceImpl extends ServiceImpl<ReservationMapper, Reservation> implements ReservationService {

    private final ReservationMapper reservationMapper;

    /**
     * 分页查询[预约管理]列表
     *
     * @param page  分页参数
     * @param param Reservation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Reservation> getPage(PageParam<Reservation> page, Reservation param) {
        return reservationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Reservation getInfoById(String id) {
        return reservationMapper.getInfoById(id);
    }

    ;

}


