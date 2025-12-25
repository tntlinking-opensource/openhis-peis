package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdEmphasisAskSymptomMapper;
import com.center.medical.datamove.common.bean.model.MdEmphasisAskSymptom;
import com.center.medical.datamove.common.service.MdEmphasisAskSymptomService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC重点询问症状表(MdEmphasisAskSymptom)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:15
 */
@Slf4j
@Service("mdEmphasisAskSymptomService")
@RequiredArgsConstructor
public class MdEmphasisAskSymptomServiceImpl extends ServiceImpl<MdEmphasisAskSymptomMapper, MdEmphasisAskSymptom> implements MdEmphasisAskSymptomService {

    private final MdEmphasisAskSymptomMapper mdEmphasisAskSymptomMapper;

    /**
     * 分页查询[JC重点询问症状表]列表
     *
     * @param page  分页参数
     * @param param MdEmphasisAskSymptom查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdEmphasisAskSymptom> getPage(PageParam<MdEmphasisAskSymptom> page, MdEmphasisAskSymptom param) {
        return mdEmphasisAskSymptomMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdEmphasisAskSymptom getInfoById(String id) {
        return mdEmphasisAskSymptomMapper.getInfoById(id);
    }

}


