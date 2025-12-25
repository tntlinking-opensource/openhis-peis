package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.PeispatientChargeOtherMapper;
import com.center.medical.bean.model.PeispatientChargeOther;
import com.center.medical.service.PeispatientChargeOtherService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者其他缴费(PeispatientChargeOther)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:10
 */
@Slf4j
@Service("peispatientChargeOtherService")
@RequiredArgsConstructor
public class PeispatientChargeOtherServiceImpl extends ServiceImpl<PeispatientChargeOtherMapper, PeispatientChargeOther> implements PeispatientChargeOtherService {

    private final PeispatientChargeOtherMapper peispatientChargeOtherMapper;

    /**
     * 分页查询[体检者其他缴费]列表
     *
     * @param page  分页参数
     * @param param PeispatientChargeOther查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeispatientChargeOther> getList(PageParam<PeispatientChargeOther> page, PeispatientChargeOther param) {
        return peispatientChargeOtherMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PeispatientChargeOther getInfoById(String id) {
        return peispatientChargeOtherMapper.getInfoById(id);
    }

}

