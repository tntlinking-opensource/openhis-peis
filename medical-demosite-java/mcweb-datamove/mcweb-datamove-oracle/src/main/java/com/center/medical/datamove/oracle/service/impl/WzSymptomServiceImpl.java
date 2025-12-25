package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.WzSymptomMapper;
import com.center.medical.datamove.oracle.bean.model.WzSymptom;
import com.center.medical.datamove.oracle.service.WzSymptomService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——症状(WzSymptom)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:30:51
 */
@Slf4j
@Service("wzSymptomService")
@RequiredArgsConstructor
public class WzSymptomServiceImpl extends ServiceImpl<WzSymptomMapper, WzSymptom> implements WzSymptomService {

    private final WzSymptomMapper wzSymptomMapper;

    /**
     * 分页查询[KS问诊——症状]列表
     *
     * @param page  分页参数
     * @param param WzSymptom查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WzSymptom> getPage(PageParam<WzSymptom> page, WzSymptom param) {
        return wzSymptomMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public WzSymptom getInfoById(String id) {
        return wzSymptomMapper.getInfoById(id);
    }

}


