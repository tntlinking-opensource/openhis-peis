package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.DrugDiseaseTypeMapper;
import com.center.medical.bean.model.DrugDiseaseType;
import com.center.medical.service.DrugDiseaseTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 禁忌疾病分类(DrugDiseaseType)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:17
 */
@Slf4j
@Service("drugDiseaseTypeService")
@RequiredArgsConstructor
public class DrugDiseaseTypeServiceImpl extends ServiceImpl<DrugDiseaseTypeMapper, DrugDiseaseType> implements DrugDiseaseTypeService {

    private final DrugDiseaseTypeMapper drugDiseaseTypeMapper;

    /**
     * 分页查询[禁忌疾病分类]列表
     *
     * @param page  分页参数
     * @param param DrugDiseaseType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DrugDiseaseType> getList(PageParam<DrugDiseaseType> page, DrugDiseaseType param) {
        return drugDiseaseTypeMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public DrugDiseaseType getInfoById(String id) {
        return drugDiseaseTypeMapper.getInfoById(id);
    }

    ;

}

