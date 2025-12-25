package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PeispatientReservationChargeMapper;
import com.center.medical.datamove.oracle.bean.model.PeispatientReservationCharge;
import com.center.medical.datamove.oracle.service.PeispatientReservationChargeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者结算表(PeispatientReservationCharge)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:24
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
    public IPage<PeispatientReservationCharge> getPage(PageParam<PeispatientReservationCharge> page, PeispatientReservationCharge param) {
        return peispatientReservationChargeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PeispatientReservationCharge getInfoById(String id) {
        return peispatientReservationChargeMapper.getInfoById(id);
    }

    ;

}


