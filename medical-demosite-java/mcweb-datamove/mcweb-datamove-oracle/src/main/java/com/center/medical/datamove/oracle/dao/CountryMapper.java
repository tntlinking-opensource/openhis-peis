package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Country;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC国家(Country)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:17:52
 */
public interface CountryMapper extends BaseMapper<Country> {

    /**
     * 分页查询[JC国家]列表
     *
     * @param page  分页参数
     * @param param Country查询参数
     * @return 分页数据
     */
    IPage<Country> getPage(PageParam<Country> page, @Param("param") Country param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Country getInfoById(@Param("id") String id);

}
