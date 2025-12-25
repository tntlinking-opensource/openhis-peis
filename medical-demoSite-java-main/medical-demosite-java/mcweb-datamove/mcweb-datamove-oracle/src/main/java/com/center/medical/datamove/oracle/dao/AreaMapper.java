package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Area;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 籍贯表(Area)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:12:12
 */
public interface AreaMapper extends BaseMapper<Area> {

    /**
     * 分页查询[籍贯表]列表
     *
     * @param page  分页参数
     * @param param Area查询参数
     * @return 分页数据
     */
    IPage<Area> getPage(PageParam<Area> page, @Param("param") Area param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Area getInfoById(@Param("id") String id);

}
