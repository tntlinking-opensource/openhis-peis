package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.DrugDiseaseType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * jc禁忌疾病分类(DrugDiseaseType)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:26
 */
public interface DrugDiseaseTypeMapper extends BaseMapper<DrugDiseaseType> {

    /**
     * 分页查询[jc禁忌疾病分类]列表
     *
     * @param page  分页参数
     * @param param DrugDiseaseType查询参数
     * @return 分页数据
     */
    IPage<DrugDiseaseType> getPage(PageParam<DrugDiseaseType> page, @Param("param") DrugDiseaseType param);


}
