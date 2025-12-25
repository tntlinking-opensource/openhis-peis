package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdOccupationSymptom;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC职业症状(MdOccupationSymptom)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:28
 */
public interface MdOccupationSymptomMapper extends BaseMapper<MdOccupationSymptom> {

    /**
     * 分页查询[JC职业症状]列表
     *
     * @param page  分页参数
     * @param param MdOccupationSymptom查询参数
     * @return 分页数据
     */
    IPage<MdOccupationSymptom> getPage(PageParam<MdOccupationSymptom> page, @Param("param") MdOccupationSymptom param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdOccupationSymptom getInfoById(@Param("id") String id);

}
