package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.DrugDiseaseType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 禁忌疾病分类(DrugDiseaseType)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:17
 */
public interface DrugDiseaseTypeService extends IService<DrugDiseaseType> {

    /**
     * 分页查询[禁忌疾病分类]列表
     *
     * @param page  分页参数
     * @param param DrugDiseaseType查询参数
     * @return 分页数据
     */
    IPage<DrugDiseaseType> getList(PageParam<DrugDiseaseType> page, DrugDiseaseType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    DrugDiseaseType getInfoById(String id);

}

