package com.center.medical.appadmin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.appadmin.bean.model.BsSettlement;
import com.center.medical.appadmin.bean.param.BsSettlementParam;
import com.center.medical.appadmin.bean.vo.BSGetTotalVo;
import com.center.medical.appadmin.bean.vo.BsSettlementVo;
import com.center.medical.appadmin.dao.BsSettlementMapper;
import com.center.medical.appadmin.service.BsSettlementService;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
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
    @DataSource(value = DataSourceType.APPLET)
    public IPage<BsSettlementVo> getPage(PageParam<BsSettlementVo> page, BsSettlementParam param) {
        return bsSettlementMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键settlementId
     * @return 详情信息
     */
    @Override
    @DataSource(value = DataSourceType.APPLET)
    public BsSettlement getInfoById(String id) {
        return bsSettlementMapper.getInfoById(id);
    }

    /**
     * 获取总额
     * @param param
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.APPLET)
    public BSGetTotalVo getTotal(BsSettlementParam param) {
        return bsSettlementMapper.getTotal(param);
    }
}

