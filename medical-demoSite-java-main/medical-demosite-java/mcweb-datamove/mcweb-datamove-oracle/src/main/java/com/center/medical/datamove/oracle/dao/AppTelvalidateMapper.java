package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.AppTelvalidate;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (AppTelvalidate)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:12:00
 */
public interface AppTelvalidateMapper extends BaseMapper<AppTelvalidate> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param AppTelvalidate查询参数
     * @return 分页数据
     */
    IPage<AppTelvalidate> getPage(PageParam<AppTelvalidate> page, @Param("param") AppTelvalidate param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppTelvalidate getInfoById(@Param("id") String id);

}
