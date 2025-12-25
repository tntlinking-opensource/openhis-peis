package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.BaseDictionary;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 字典(BaseDictionary)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:12:28
 */
public interface BaseDictionaryMapper extends BaseMapper<BaseDictionary> {

    /**
     * 分页查询[字典]列表
     *
     * @param page  分页参数
     * @param param BaseDictionary查询参数
     * @return 分页数据
     */
    IPage<BaseDictionary> getPage(PageParam<BaseDictionary> page, @Param("param") BaseDictionary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BaseDictionary getInfoById(@Param("id") String id);

}
