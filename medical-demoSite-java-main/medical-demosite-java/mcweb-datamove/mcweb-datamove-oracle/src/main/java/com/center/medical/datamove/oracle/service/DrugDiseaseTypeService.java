package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.DrugDiseaseType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * jc禁忌疾病分类(DrugDiseaseType)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:18:26
 */
public interface DrugDiseaseTypeService extends IService<DrugDiseaseType> {

    /**
     * 分页查询[jc禁忌疾病分类]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<DrugDiseaseType> getPage(PageParam<DrugDiseaseType> page, DrugDiseaseType param);


}

