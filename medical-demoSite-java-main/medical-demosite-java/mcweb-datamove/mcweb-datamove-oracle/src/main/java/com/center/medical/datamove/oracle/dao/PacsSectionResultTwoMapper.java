package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.PacsSectionResultTwo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (PacsSectionResultTwo)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:54
 */
public interface PacsSectionResultTwoMapper extends BaseMapper<PacsSectionResultTwo> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PacsSectionResultTwo查询参数
     * @return 分页数据
     */
    IPage<PacsSectionResultTwo> getPage(PageParam<PacsSectionResultTwo> page, @Param("param") PacsSectionResultTwo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PacsSectionResultTwo getInfoById(@Param("id") String id);

}
