package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SampleConnectMapper;
import com.center.medical.datamove.oracle.bean.model.SampleConnect;
import com.center.medical.datamove.oracle.service.SampleConnectService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS样本交接(SampleConnect)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:32
 */
@Slf4j
@Service("sampleConnectService")
@RequiredArgsConstructor
public class SampleConnectServiceImpl extends ServiceImpl<SampleConnectMapper, SampleConnect> implements SampleConnectService {

    private final SampleConnectMapper sampleConnectMapper;

    /**
     * 分页查询[KS样本交接]列表
     *
     * @param page  分页参数
     * @param param SampleConnect查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SampleConnect> getPage(PageParam<SampleConnect> page, SampleConnect param) {
        return sampleConnectMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SampleConnect getInfoById(String id) {
        return sampleConnectMapper.getInfoById(id);
    }

}


