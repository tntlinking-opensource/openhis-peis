package com.center.medical.reservation.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.dto.ReservationNotifyDto;
import com.center.medical.reservation.bean.model.ReservationNotify;
import com.center.medical.reservation.dao.ReservationNotifyMapper;
import com.center.medical.reservation.service.ReservationNotifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 预约信息变更通知记录(ReservationNotify)服务实现类
 *
 * @author makejava
 * @since 2023-09-21 10:56:58
 */
@Slf4j
@Service("reservationNotifyService")
@RequiredArgsConstructor
public class ReservationNotifyServiceImpl extends ServiceImpl<ReservationNotifyMapper, ReservationNotify> implements ReservationNotifyService {

    private final ReservationNotifyMapper reservationNotifyMapper;

    /**
     * 分页查询[预约信息变更通知记录]列表
     *
     * @param page  分页参数
     * @param param ReservationNotify查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReservationNotify> getPage(PageParam<ReservationNotify> page, ReservationNotify param) {
        return reservationNotifyMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ReservationNotify getInfoById(String id) {
        return reservationNotifyMapper.getInfoById(id);
    }

    /**
     * 获取待通知的列表
     *
     * @param num 数量
     * @return
     */
    @Override
    public List<ReservationNotifyDto> getNotifyList(Integer num) {
        return reservationNotifyMapper.getNotifyList(num);
    }

}

