package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.BasMerge;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (BasMerge)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:12:19
 */
public interface BasMergeMapper extends BaseMapper<BasMerge> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param BasMerge查询参数
     * @return 分页数据
     */
    IPage<BasMerge> getPage(PageParam<BasMerge> page, @Param("param") BasMerge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BasMerge getInfoById(@Param("id") String id);

}
