package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.PeispatientChargeMainMapper;
import com.center.medical.bean.model.PeispatientChargeMain;
import com.center.medical.service.PeispatientChargeMainService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者费用主表(PeispatientChargeMain)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:24
 */
@Slf4j
@Service("peispatientChargeMainService")
@RequiredArgsConstructor
public class PeispatientChargeMainServiceImpl extends ServiceImpl<PeispatientChargeMainMapper, PeispatientChargeMain> implements PeispatientChargeMainService {

    private final PeispatientChargeMainMapper peispatientChargeMainMapper;

    /**
     * 分页查询[体检者费用主表]列表
     *
     * @param page  分页参数
     * @param param PeispatientChargeMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeispatientChargeMain> getList(PageParam<PeispatientChargeMain> page, PeispatientChargeMain param) {
        return peispatientChargeMainMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PeispatientChargeMain getInfoById(String id) {
        return peispatientChargeMainMapper.getInfoById(id);
    }

}

