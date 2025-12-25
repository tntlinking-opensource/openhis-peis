package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Country;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC国家(Country)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:28
 */
public interface CountryMapper extends BaseMapper<Country> {

    /**
     * 分页查询[JC国家]列表
     *
     * @param page  分页参数
     * @param param Country查询参数
     * @return 分页数据
     */
    IPage<Country> getList(PageParam<Country> page, @Param("param") Country param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Country getInfoById(@Param("id") String id);

}
