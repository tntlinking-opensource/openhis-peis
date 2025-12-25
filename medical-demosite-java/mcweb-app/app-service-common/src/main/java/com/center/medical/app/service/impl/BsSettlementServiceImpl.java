package com.center.medical.app.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.app.bean.model.BsSettlement;
import com.center.medical.app.common.util.PageParam;
import com.center.medical.app.dao.BsSettlementMapper;
import com.center.medical.app.service.BsSettlementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 业务结算表(BsSettlement)服务实现类
 *
 * @author ay
 * @since 2024-06-17 16:01:58
 */
@Slf4j
@Service("bsSettlementService")
@RequiredArgsConstructor
public class BsSettlementServiceImpl extends ServiceImpl<BsSettlementMapper, BsSettlement> implements BsSettlementService {

    private final BsSettlementMapper bsSettlementMapper;

    /**
     * 分页查询[业务结算表]列表
     *
     * @param page  分页参数
     * @param param BsSettlement查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BsSettlement> getPage(PageParam<BsSettlement> page, BsSettlement param) {
        return bsSettlementMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键settlementId
     * @return 详情信息
     */
    @Override
    public BsSettlement getInfoById(String id) {
        return bsSettlementMapper.getInfoById(id);
    }

}

