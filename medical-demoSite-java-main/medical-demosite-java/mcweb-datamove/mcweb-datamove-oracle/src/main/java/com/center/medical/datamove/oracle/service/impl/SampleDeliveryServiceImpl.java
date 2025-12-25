package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SampleDeliveryMapper;
import com.center.medical.datamove.oracle.bean.model.SampleDelivery;
import com.center.medical.datamove.oracle.service.SampleDeliveryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS样本录入(SampleDelivery)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:34
 */
@Slf4j
@Service("sampleDeliveryService")
@RequiredArgsConstructor
public class SampleDeliveryServiceImpl extends ServiceImpl<SampleDeliveryMapper, SampleDelivery> implements SampleDeliveryService {

    private final SampleDeliveryMapper sampleDeliveryMapper;

    /**
     * 分页查询[KS样本录入]列表
     *
     * @param page  分页参数
     * @param param SampleDelivery查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SampleDelivery> getPage(PageParam<SampleDelivery> page, SampleDelivery param) {
        return sampleDeliveryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SampleDelivery getInfoById(String id) {
        return sampleDeliveryMapper.getInfoById(id);
    }

}


