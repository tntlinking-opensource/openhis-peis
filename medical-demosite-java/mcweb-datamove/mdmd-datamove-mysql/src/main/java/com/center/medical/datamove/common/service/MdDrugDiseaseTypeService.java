package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdDrugDiseaseType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * jc禁忌疾病分类(MdDrugDiseaseType)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:14
 */
public interface MdDrugDiseaseTypeService extends IService<MdDrugDiseaseType> {

    /**
     * 分页查询[jc禁忌疾病分类]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdDrugDiseaseType> getPage(PageParam<MdDrugDiseaseType> page, MdDrugDiseaseType param);


}

