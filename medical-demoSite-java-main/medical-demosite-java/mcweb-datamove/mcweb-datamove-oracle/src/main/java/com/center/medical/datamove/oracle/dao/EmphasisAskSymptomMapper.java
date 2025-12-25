package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.EmphasisAskSymptom;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC重点询问症状表(EmphasisAskSymptom)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:36
 */
public interface EmphasisAskSymptomMapper extends BaseMapper<EmphasisAskSymptom> {

    /**
     * 分页查询[JC重点询问症状表]列表
     *
     * @param page  分页参数
     * @param param EmphasisAskSymptom查询参数
     * @return 分页数据
     */
    IPage<EmphasisAskSymptom> getPage(PageParam<EmphasisAskSymptom> page, @Param("param") EmphasisAskSymptom param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    EmphasisAskSymptom getInfoById(@Param("id") String id);

}
