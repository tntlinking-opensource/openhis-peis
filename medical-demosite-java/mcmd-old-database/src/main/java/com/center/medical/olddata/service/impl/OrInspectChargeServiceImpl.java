package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrInspectCharge;
import com.center.medical.olddata.dao.OrInspectChargeMapper;
import com.center.medical.olddata.service.OrInspectChargeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * JC检查项目收费项目关联表(InspectCharge)服务实现类
 *
 * @author ay
 * @since 2024-07-13 14:27:29
 */
@Slf4j
@Service("orInspectChargeService")
@RequiredArgsConstructor
public class OrInspectChargeServiceImpl extends ServiceImpl<OrInspectChargeMapper, OrInspectCharge> implements OrInspectChargeService {

    private final OrInspectChargeMapper orInspectChargeMapper;

    /**
     * 分页查询[JC检查项目收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param InspectCharge查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrInspectCharge> getPage(PageParam<OrInspectCharge> page, OrInspectCharge param) {
        return orInspectChargeMapper.getPage(page, param);
    }


    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrInspectCharge getInfoById(String id) {
        return orInspectChargeMapper.getInfoById(id);
    }

    /**
     * 通过收费项目查询
     * @param chargeId
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrInspectCharge> getInfoBychargeId(String chargeId) {
        return orInspectChargeMapper.selectList(new LambdaQueryWrapper<OrInspectCharge>()
                .eq(OrInspectCharge::getChargeId,chargeId));
    }
}

