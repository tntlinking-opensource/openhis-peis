package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdSampleDeliveryMapper;
import com.center.medical.datamove.common.bean.model.MdSampleDelivery;
import com.center.medical.datamove.common.service.MdSampleDeliveryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS样本录入(MdSampleDelivery)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:25
 */
@Slf4j
@Service("mdSampleDeliveryService")
@RequiredArgsConstructor
public class MdSampleDeliveryServiceImpl extends ServiceImpl<MdSampleDeliveryMapper, MdSampleDelivery> implements MdSampleDeliveryService {

    private final MdSampleDeliveryMapper mdSampleDeliveryMapper;

    /**
     * 分页查询[KS样本录入]列表
     *
     * @param page  分页参数
     * @param param MdSampleDelivery查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdSampleDelivery> getPage(PageParam<MdSampleDelivery> page, MdSampleDelivery param) {
        return mdSampleDeliveryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdSampleDelivery getInfoById(String id) {
        return mdSampleDeliveryMapper.getInfoById(id);
    }

}


