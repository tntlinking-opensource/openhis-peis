package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdWzSymptom;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——症状(MdWzSymptom)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:24
 */
public interface MdWzSymptomMapper extends BaseMapper<MdWzSymptom> {

    /**
     * 分页查询[KS问诊——症状]列表
     *
     * @param page  分页参数
     * @param param MdWzSymptom查询参数
     * @return 分页数据
     */
    IPage<MdWzSymptom> getPage(PageParam<MdWzSymptom> page, @Param("param") MdWzSymptom param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdWzSymptom getInfoById(@Param("id") String id);

}
