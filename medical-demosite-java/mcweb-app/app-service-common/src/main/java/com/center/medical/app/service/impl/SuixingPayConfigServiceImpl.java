package com.center.medical.app.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.app.bean.model.SuixingPayConfig;
import com.center.medical.app.common.util.PageParam;
import com.center.medical.app.dao.SuixingPayConfigMapper;
import com.center.medical.app.service.SuixingPayConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 随行支付配置参数(SuixingPayConfig)服务实现类
 *
 * @author ay
 * @since 2024-07-12 17:05:40
 */
@Slf4j
@Service("suixingPayConfigService")
@RequiredArgsConstructor
public class SuixingPayConfigServiceImpl extends ServiceImpl<SuixingPayConfigMapper, SuixingPayConfig> implements SuixingPayConfigService {

    private final SuixingPayConfigMapper suixingPayConfigMapper;

    /**
     * 分页查询[随行支付配置参数]列表
     *
     * @param page  分页参数
     * @param param SuixingPayConfig查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SuixingPayConfig> getPage(PageParam<SuixingPayConfig> page, SuixingPayConfig param) {
        return suixingPayConfigMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SuixingPayConfig getInfoById(String id) {
        return suixingPayConfigMapper.getInfoById(id);
    }

    /**
     * 通过分中心id查询
     * @param branchId
     * @return
     */
    @Override
    public SuixingPayConfig getInfoByFzx(String branchId) {
        return suixingPayConfigMapper.getInfoByFzx(branchId);
    }
}

