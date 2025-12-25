package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdWzSymptomMapper;
import com.center.medical.datamove.common.bean.model.MdWzSymptom;
import com.center.medical.datamove.common.service.MdWzSymptomService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——症状(MdWzSymptom)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:25
 */
@Slf4j
@Service("mdWzSymptomService")
@RequiredArgsConstructor
public class MdWzSymptomServiceImpl extends ServiceImpl<MdWzSymptomMapper, MdWzSymptom> implements MdWzSymptomService {

    private final MdWzSymptomMapper mdWzSymptomMapper;

    /**
     * 分页查询[KS问诊——症状]列表
     *
     * @param page  分页参数
     * @param param MdWzSymptom查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdWzSymptom> getPage(PageParam<MdWzSymptom> page, MdWzSymptom param) {
        return mdWzSymptomMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdWzSymptom getInfoById(String id) {
        return mdWzSymptomMapper.getInfoById(id);
    }

}


