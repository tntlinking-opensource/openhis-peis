package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.KdRemittanceMapper;
import com.center.medical.datamove.common.bean.model.KdRemittance;
import com.center.medical.datamove.common.service.KdRemittanceService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * kingdeeremittance(KdRemittance)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:04
 */
@Slf4j
@Service("kdRemittanceService")
@RequiredArgsConstructor
public class KdRemittanceServiceImpl extends ServiceImpl<KdRemittanceMapper, KdRemittance> implements KdRemittanceService {

    private final KdRemittanceMapper kdRemittanceMapper;

    /**
     * 分页查询[kingdeeremittance]列表
     *
     * @param page  分页参数
     * @param param KdRemittance查询参数
     * @return 分页数据
     */
    @Override
    public IPage<KdRemittance> getPage(PageParam<KdRemittance> page, KdRemittance param) {
        return kdRemittanceMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键fid
     * @return 详情信息
     */
    @Override
    public KdRemittance getInfoById(String id) {
        return kdRemittanceMapper.getInfoById(id);
    }

}


