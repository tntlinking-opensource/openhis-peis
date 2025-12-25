package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrSectionResultMain;
import org.apache.ibatis.annotations.Param;

/**
 * KS科室检查结果主表(SectionResultMain)数据库访问层
 *
 * @author ay
 * @since 2024-06-05 14:39:40
 */
public interface OrSectionResultMainMapper extends BaseMapper<OrSectionResultMain> {

    /**
     * 分页查询[KS科室检查结果主表]列表
     *
     * @param page  分页参数
     * @param param SectionResultMain查询参数
     * @return 分页数据
     */
    IPage<OrSectionResultMain> getPage(PageParam<OrSectionResultMain> page, @Param("param") OrSectionResultMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrSectionResultMain getInfoById(@Param("id") String id);

}
