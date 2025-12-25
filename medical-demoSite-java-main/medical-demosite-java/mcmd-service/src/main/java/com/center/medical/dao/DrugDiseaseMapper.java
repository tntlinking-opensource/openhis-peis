package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.DrugDisease;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 禁忌疾病(DrugDisease)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:49
 */
public interface DrugDiseaseMapper extends BaseMapper<DrugDisease> {

    /**
     * 分页查询[禁忌疾病]列表
     *
     * @param page  分页参数
     * @param param DrugDisease查询参数
     * @return 分页数据
     */
    IPage<DrugDisease> getList(PageParam<DrugDisease> page, @Param("param") DrugDisease param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    DrugDisease getInfoById(@Param("id") String id);

}
