package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPeispatientReservationChargeMapper;
import com.center.medical.datamove.common.bean.model.MdPeispatientReservationCharge;
import com.center.medical.datamove.common.service.MdPeispatientReservationChargeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者结算表(MdPeispatientReservationCharge)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:13
 */
@Slf4j
@Service("mdPeispatientReservationChargeService")
@RequiredArgsConstructor
public class MdPeispatientReservationChargeServiceImpl extends ServiceImpl<MdPeispatientReservationChargeMapper, MdPeispatientReservationCharge> implements MdPeispatientReservationChargeService {

    private final MdPeispatientReservationChargeMapper mdPeispatientReservationChargeMapper;

    /**
     * 分页查询[体检者结算表]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientReservationCharge查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeispatientReservationCharge> getPage(PageParam<MdPeispatientReservationCharge> page, MdPeispatientReservationCharge param) {
        return mdPeispatientReservationChargeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPeispatientReservationCharge getInfoById(String id) {
        return mdPeispatientReservationChargeMapper.getInfoById(id);
    }

}


