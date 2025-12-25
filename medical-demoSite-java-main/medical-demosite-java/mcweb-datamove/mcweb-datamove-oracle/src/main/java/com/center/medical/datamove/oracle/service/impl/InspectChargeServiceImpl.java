package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.InspectChargeMapper;
import com.center.medical.datamove.oracle.bean.model.InspectCharge;
import com.center.medical.datamove.oracle.service.InspectChargeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC检查项目收费项目关联表(InspectCharge)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:15
 */
@Slf4j
@Service("inspectChargeService")
@RequiredArgsConstructor
public class InspectChargeServiceImpl extends ServiceImpl<InspectChargeMapper, InspectCharge> implements InspectChargeService {

    private final InspectChargeMapper inspectChargeMapper;

    /**
     * 分页查询[JC检查项目收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param InspectCharge查询参数
     * @return 分页数据
     */
    @Override
    public IPage<InspectCharge> getPage(PageParam<InspectCharge> page, InspectCharge param) {
        return inspectChargeMapper.getPage(page, param);
    }


}


