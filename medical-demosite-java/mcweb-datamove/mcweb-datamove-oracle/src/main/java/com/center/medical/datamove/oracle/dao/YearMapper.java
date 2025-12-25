package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Year;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 年份表(Year)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:30:54
 */
public interface YearMapper extends BaseMapper<Year> {

    /**
     * 分页查询[年份表]列表
     *
     * @param page  分页参数
     * @param param Year查询参数
     * @return 分页数据
     */
    IPage<Year> getPage(PageParam<Year> page, @Param("param") Year param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Year getInfoById(@Param("id") String id);

}
