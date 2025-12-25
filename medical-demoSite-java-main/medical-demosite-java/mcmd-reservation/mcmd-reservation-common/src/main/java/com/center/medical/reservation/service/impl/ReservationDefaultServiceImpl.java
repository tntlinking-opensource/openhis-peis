package com.center.medical.reservation.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.BranchScope;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.model.ReservationDefault;
import com.center.medical.reservation.bean.param.ReservationPageParam;
import com.center.medical.reservation.dao.ReservationDefaultMapper;
import com.center.medical.reservation.service.ReservationDefaultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 预约各检区默认设置(ReservationDefault)表服务实现类
 *
 * @author ay
 * @since 2023-03-18 08:54:14
 */
@Slf4j
@Service("reservationDefaultService")
@RequiredArgsConstructor
public class ReservationDefaultServiceImpl extends ServiceImpl<ReservationDefaultMapper, ReservationDefault> implements ReservationDefaultService {

    private final ReservationDefaultMapper reservationDefaultMapper;

    /**
     * 分页查询[预约各检区默认设置]列表
     *
     * @param page  分页参数
     * @param param ReservationDefault查询参数
     * @return 分页数据
     */
    @Override
    @BranchScope(alias = "rd.branch_id")
    public IPage<ReservationDefault> getList(PageParam<ReservationDefault> page, ReservationPageParam param) {
        return reservationDefaultMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ReservationDefault getInfoById(String id) {
        return reservationDefaultMapper.getInfoById(id);
    }

}

