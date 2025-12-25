package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.GenTable;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 代码生成业务表(GenTable)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:03
 */
public interface GenTableMapper extends BaseMapper<GenTable> {

    /**
     * 分页查询[代码生成业务表]列表
     *
     * @param page  分页参数
     * @param param GenTable查询参数
     * @return 分页数据
     */
    IPage<GenTable> getPage(PageParam<GenTable> page, @Param("param") GenTable param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键tableId
     * @return 详情信息
     */
    GenTable getInfoById(@Param("id") Long id);

}
