package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.EmphasisAskSymptom;
import org.apache.ibatis.annotations.Param;

/**
 * JC重点询问症状表(EmphasisAskSymptom)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:14
 */
public interface EmphasisAskSymptomMapper extends BaseMapper<EmphasisAskSymptom> {

    /**
     * 分页查询[JC重点询问症状表]列表
     *
     * @param page  分页参数
     * @param param EmphasisAskSymptom查询参数
     * @return 分页数据
     */
    IPage<EmphasisAskSymptom> getList(PageParam<EmphasisAskSymptom> page, @Param("param") EmphasisAskSymptom param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    EmphasisAskSymptom getInfoById(@Param("id") String id);

    /**
     * 执行sql
     * @param sql
     */
    void implementSql( @Param("param")String sql);
}
