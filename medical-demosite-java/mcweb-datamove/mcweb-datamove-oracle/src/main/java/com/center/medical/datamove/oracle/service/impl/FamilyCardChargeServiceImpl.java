package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.FamilyCardChargeMapper;
import com.center.medical.datamove.oracle.bean.model.FamilyCardCharge;
import com.center.medical.datamove.oracle.service.FamilyCardChargeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (FamilyCardCharge)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:42
 */
@Slf4j
@Service("familyCardChargeService")
@RequiredArgsConstructor
public class FamilyCardChargeServiceImpl extends ServiceImpl<FamilyCardChargeMapper, FamilyCardCharge> implements FamilyCardChargeService {

    private final FamilyCardChargeMapper familyCardChargeMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param FamilyCardCharge查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FamilyCardCharge> getPage(PageParam<FamilyCardCharge> page, FamilyCardCharge param) {
        return familyCardChargeMapper.getPage(page, param);
    }


}


