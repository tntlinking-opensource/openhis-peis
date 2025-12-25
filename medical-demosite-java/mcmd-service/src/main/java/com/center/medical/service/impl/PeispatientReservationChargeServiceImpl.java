package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.PeispatientReservationChargeMapper;
import com.center.medical.bean.model.PeispatientReservationCharge;
import com.center.medical.service.PeispatientReservationChargeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者结算表(PeispatientReservationCharge)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:03
 */
@Slf4j
@Service("peispatientReservationChargeService")
@RequiredArgsConstructor
public class PeispatientReservationChargeServiceImpl extends ServiceImpl<PeispatientReservationChargeMapper, PeispatientReservationCharge> implements PeispatientReservationChargeService {

    private final PeispatientReservationChargeMapper peispatientReservationChargeMapper;

    /**
     * 分页查询[体检者结算表]列表
     *
     * @param page  分页参数
     * @param param PeispatientReservationCharge查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeispatientReservationCharge> getList(PageParam<PeispatientReservationCharge> page, PeispatientReservationCharge param) {
        return peispatientReservationChargeMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PeispatientReservationCharge getInfoById(String id) {
        return peispatientReservationChargeMapper.getInfoById(id);
    }

}

