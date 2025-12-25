package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Year;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 年份表(Year)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:41
 */
public interface YearMapper extends BaseMapper<Year> {

    /**
     * 分页查询[年份表]列表
     *
     * @param page  分页参数
     * @param param Year查询参数
     * @return 分页数据
     */
    IPage<Year> getList(PageParam<Year> page, @Param("param") Year param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Year getInfoById(@Param("id") String id);

}
