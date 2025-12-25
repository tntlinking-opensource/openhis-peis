package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.BaseDictionaryClass;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 字典类型(BaseDictionaryClass)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:44:56
 */
public interface BaseDictionaryClassMapper extends BaseMapper<BaseDictionaryClass> {

    /**
     * 分页查询[字典类型]列表
     *
     * @param page  分页参数
     * @param param BaseDictionaryClass查询参数
     * @return 分页数据
     */
    IPage<BaseDictionaryClass> getPage(PageParam<BaseDictionaryClass> page, @Param("param") BaseDictionaryClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BaseDictionaryClass getInfoById(@Param("id") String id);

}
