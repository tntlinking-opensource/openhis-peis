package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PacsInspectChargeMapper;
import com.center.medical.datamove.oracle.bean.model.PacsInspectCharge;
import com.center.medical.datamove.oracle.service.PacsInspectChargeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (PacsInspectCharge)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:44
 */
@Slf4j
@Service("pacsInspectChargeService")
@RequiredArgsConstructor
public class PacsInspectChargeServiceImpl extends ServiceImpl<PacsInspectChargeMapper, PacsInspectCharge> implements PacsInspectChargeService {

    private final PacsInspectChargeMapper pacsInspectChargeMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PacsInspectCharge查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsInspectCharge> getPage(PageParam<PacsInspectCharge> page, PacsInspectCharge param) {
        return pacsInspectChargeMapper.getPage(page, param);
    }


}


