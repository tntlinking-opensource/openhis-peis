package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.BaseDictionaryMatch;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检系统-职业卫生管理平台 字典匹配(BaseDictionaryMatch)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:12:31
 */
public interface BaseDictionaryMatchMapper extends BaseMapper<BaseDictionaryMatch> {

    /**
     * 分页查询[体检系统-职业卫生管理平台 字典匹配]列表
     *
     * @param page  分页参数
     * @param param BaseDictionaryMatch查询参数
     * @return 分页数据
     */
    IPage<BaseDictionaryMatch> getPage(PageParam<BaseDictionaryMatch> page, @Param("param") BaseDictionaryMatch param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BaseDictionaryMatch getInfoById(@Param("id") String id);

}
