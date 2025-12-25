package com.center.medical.abteilung.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS科室检查结果主表(SectionResultMain)表数据库访问层
 *
 * @author ay
 * @since 2023-02-20 19:16:34
 */
public interface bmdMapper extends BaseMapper<SectionResultMain> {

    /**
    * 分页查询[KS科室检查结果主表]列表
    *
    * @param page 分页参数
    * @param param SectionResultMain查询参数
    * @return 分页数据
    */
    IPage<SectionResultMain> getList(PageParam<SectionResultMain> page, @Param("param") SectionResultMain param);

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    SectionResultMain getInfoById(@Param("id") String id);

}
