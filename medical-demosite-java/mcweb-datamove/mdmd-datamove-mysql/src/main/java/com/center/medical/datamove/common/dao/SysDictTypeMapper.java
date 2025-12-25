package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.SysDictType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 字典类型表(SysDictType)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:35
 */
public interface SysDictTypeMapper extends BaseMapper<SysDictType> {

    /**
     * 分页查询[字典类型表]列表
     *
     * @param page  分页参数
     * @param param SysDictType查询参数
     * @return 分页数据
     */
    IPage<SysDictType> getPage(PageParam<SysDictType> page, @Param("param") SysDictType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键dictId
     * @return 详情信息
     */
    SysDictType getInfoById(@Param("id") Long id);

}
