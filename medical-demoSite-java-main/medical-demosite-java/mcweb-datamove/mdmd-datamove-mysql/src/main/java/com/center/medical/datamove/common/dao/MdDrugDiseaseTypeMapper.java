package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdDrugDiseaseType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * jc禁忌疾病分类(MdDrugDiseaseType)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:14
 */
public interface MdDrugDiseaseTypeMapper extends BaseMapper<MdDrugDiseaseType> {

    /**
     * 分页查询[jc禁忌疾病分类]列表
     *
     * @param page  分页参数
     * @param param MdDrugDiseaseType查询参数
     * @return 分页数据
     */
    IPage<MdDrugDiseaseType> getPage(PageParam<MdDrugDiseaseType> page, @Param("param") MdDrugDiseaseType param);


}
