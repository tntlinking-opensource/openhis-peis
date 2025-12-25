package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.SectionTotal;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * ZJ总检主表(SectionTotal)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:47
 */
public interface SectionTotalMapper extends BaseMapper<SectionTotal> {

    /**
     * 分页查询[ZJ总检主表]列表
     *
     * @param page  分页参数
     * @param param SectionTotal查询参数
     * @return 分页数据
     */
    IPage<SectionTotal> getPage(PageParam<SectionTotal> page, @Param("param") SectionTotal param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SectionTotal getInfoById(@Param("id") String id);

}
