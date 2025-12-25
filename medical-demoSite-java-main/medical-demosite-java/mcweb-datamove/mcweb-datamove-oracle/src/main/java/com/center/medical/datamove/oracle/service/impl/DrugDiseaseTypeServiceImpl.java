package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.DrugDiseaseTypeMapper;
import com.center.medical.datamove.oracle.bean.model.DrugDiseaseType;
import com.center.medical.datamove.oracle.service.DrugDiseaseTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * jc禁忌疾病分类(DrugDiseaseType)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:27
 */
@Slf4j
@Service("drugDiseaseTypeService")
@RequiredArgsConstructor
public class DrugDiseaseTypeServiceImpl extends ServiceImpl<DrugDiseaseTypeMapper, DrugDiseaseType> implements DrugDiseaseTypeService {

    private final DrugDiseaseTypeMapper drugDiseaseTypeMapper;

    /**
     * 分页查询[jc禁忌疾病分类]列表
     *
     * @param page  分页参数
     * @param param DrugDiseaseType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DrugDiseaseType> getPage(PageParam<DrugDiseaseType> page, DrugDiseaseType param) {
        return drugDiseaseTypeMapper.getPage(page, param);
    }


}


