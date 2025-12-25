package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SamplePersonMapper;
import com.center.medical.datamove.oracle.bean.model.SamplePerson;
import com.center.medical.datamove.oracle.service.SamplePersonService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * TJ团检报告人员样本表(SamplePerson)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:35
 */
@Slf4j
@Service("samplePersonService")
@RequiredArgsConstructor
public class SamplePersonServiceImpl extends ServiceImpl<SamplePersonMapper, SamplePerson> implements SamplePersonService {

    private final SamplePersonMapper samplePersonMapper;

    /**
     * 分页查询[TJ团检报告人员样本表]列表
     *
     * @param page  分页参数
     * @param param SamplePerson查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SamplePerson> getPage(PageParam<SamplePerson> page, SamplePerson param) {
        return samplePersonMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SamplePerson getInfoById(String id) {
        return samplePersonMapper.getInfoById(id);
    }

}


