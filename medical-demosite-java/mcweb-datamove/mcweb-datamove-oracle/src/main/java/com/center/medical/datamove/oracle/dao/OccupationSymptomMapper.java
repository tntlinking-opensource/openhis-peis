package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.OccupationSymptom;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC职业症状(OccupationSymptom)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:20
 */
public interface OccupationSymptomMapper extends BaseMapper<OccupationSymptom> {

    /**
     * 分页查询[JC职业症状]列表
     *
     * @param page  分页参数
     * @param param OccupationSymptom查询参数
     * @return 分页数据
     */
    IPage<OccupationSymptom> getPage(PageParam<OccupationSymptom> page, @Param("param") OccupationSymptom param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OccupationSymptom getInfoById(@Param("id") String id);

}
