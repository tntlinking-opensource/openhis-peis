package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.DrugDiseaseType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 禁忌疾病分类(DrugDiseaseType)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:16
 */
public interface DrugDiseaseTypeMapper extends BaseMapper<DrugDiseaseType> {

    /**
     * 分页查询[禁忌疾病分类]列表
     *
     * @param page  分页参数
     * @param param DrugDiseaseType查询参数
     * @return 分页数据
     */
    IPage<DrugDiseaseType> getList(PageParam<DrugDiseaseType> page, @Param("param") DrugDiseaseType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    DrugDiseaseType getInfoById(@Param("id") String id);

}
