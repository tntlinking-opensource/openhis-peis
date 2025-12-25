package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdOccupationSymptomMapper;
import com.center.medical.datamove.common.bean.model.MdOccupationSymptom;
import com.center.medical.datamove.common.service.MdOccupationSymptomService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC职业症状(MdOccupationSymptom)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:29
 */
@Slf4j
@Service("mdOccupationSymptomService")
@RequiredArgsConstructor
public class MdOccupationSymptomServiceImpl extends ServiceImpl<MdOccupationSymptomMapper, MdOccupationSymptom> implements MdOccupationSymptomService {

    private final MdOccupationSymptomMapper mdOccupationSymptomMapper;

    /**
     * 分页查询[JC职业症状]列表
     *
     * @param page  分页参数
     * @param param MdOccupationSymptom查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdOccupationSymptom> getPage(PageParam<MdOccupationSymptom> page, MdOccupationSymptom param) {
        return mdOccupationSymptomMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdOccupationSymptom getInfoById(String id) {
        return mdOccupationSymptomMapper.getInfoById(id);
    }

}


