package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.EmphasisAskSymptomMapper;
import com.center.medical.datamove.oracle.bean.model.EmphasisAskSymptom;
import com.center.medical.datamove.oracle.service.EmphasisAskSymptomService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC重点询问症状表(EmphasisAskSymptom)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:36
 */
@Slf4j
@Service("emphasisAskSymptomService")
@RequiredArgsConstructor
public class EmphasisAskSymptomServiceImpl extends ServiceImpl<EmphasisAskSymptomMapper, EmphasisAskSymptom> implements EmphasisAskSymptomService {

    private final EmphasisAskSymptomMapper emphasisAskSymptomMapper;

    /**
     * 分页查询[JC重点询问症状表]列表
     *
     * @param page  分页参数
     * @param param EmphasisAskSymptom查询参数
     * @return 分页数据
     */
    @Override
    public IPage<EmphasisAskSymptom> getPage(PageParam<EmphasisAskSymptom> page, EmphasisAskSymptom param) {
        return emphasisAskSymptomMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public EmphasisAskSymptom getInfoById(String id) {
        return emphasisAskSymptomMapper.getInfoById(id);
    }

}


