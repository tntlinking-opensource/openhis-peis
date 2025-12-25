package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.OccupationSymptomMapper;
import com.center.medical.datamove.oracle.bean.model.OccupationSymptom;
import com.center.medical.datamove.oracle.service.OccupationSymptomService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC职业症状(OccupationSymptom)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:21
 */
@Slf4j
@Service("occupationSymptomService")
@RequiredArgsConstructor
public class OccupationSymptomServiceImpl extends ServiceImpl<OccupationSymptomMapper, OccupationSymptom> implements OccupationSymptomService {

    private final OccupationSymptomMapper occupationSymptomMapper;

    /**
     * 分页查询[JC职业症状]列表
     *
     * @param page  分页参数
     * @param param OccupationSymptom查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OccupationSymptom> getPage(PageParam<OccupationSymptom> page, OccupationSymptom param) {
        return occupationSymptomMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OccupationSymptom getInfoById(String id) {
        return occupationSymptomMapper.getInfoById(id);
    }

}


