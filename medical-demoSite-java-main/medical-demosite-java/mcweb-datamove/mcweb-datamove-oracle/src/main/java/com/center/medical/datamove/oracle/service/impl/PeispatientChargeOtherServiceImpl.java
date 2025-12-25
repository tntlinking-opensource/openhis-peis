package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PeispatientChargeOtherMapper;
import com.center.medical.datamove.oracle.bean.model.PeispatientChargeOther;
import com.center.medical.datamove.oracle.service.PeispatientChargeOtherService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (PeispatientChargeOther)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:16
 */
@Slf4j
@Service("peispatientChargeOtherService")
@RequiredArgsConstructor
public class PeispatientChargeOtherServiceImpl extends ServiceImpl<PeispatientChargeOtherMapper, PeispatientChargeOther> implements PeispatientChargeOtherService {

    private final PeispatientChargeOtherMapper peispatientChargeOtherMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PeispatientChargeOther查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeispatientChargeOther> getPage(PageParam<PeispatientChargeOther> page, PeispatientChargeOther param) {
        return peispatientChargeOtherMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PeispatientChargeOther getInfoById(String id) {
        return peispatientChargeOtherMapper.getInfoById(id);
    }

}


