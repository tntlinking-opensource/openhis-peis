package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseDictionaryMatch;
import com.center.medical.data.bean.param.BaseDictionaryParam;
import org.apache.ibatis.annotations.Param;
/**
 * 体检系统-职业卫生管理平台 字典匹配(BaseDictionaryMatch)表数据库访问层
 *
 * @author ay
 * @since 2022-11-18 18:16:14
 */
public interface BaseDictionaryMatchMapper extends BaseMapper<BaseDictionaryMatch> {

    /**
    * 分页查询[体检系统-职业卫生管理平台 字典匹配]列表
    *
    * @param page 分页参数
    * @param param BaseDictionaryMatch查询参数
    * @return 分页数据
    */
    IPage<BaseDictionaryMatch> getList(PageParam<BaseDictionaryMatch> page, @Param("param") BaseDictionaryMatch param);

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    BaseDictionaryMatch getInfoById(@Param("id") String id);

    /**
     * 通过搜索条件分页查询列表
     * @param page
     * @param baseDictionaryParam
     * @return
     */
    PageParam<BaseDictionaryMatch> getDataMatch(PageParam<BaseDictionaryMatch> page, @Param("param") BaseDictionaryParam baseDictionaryParam);

    /**
     * 更新
     * @param baseDictionaryMatch
     */
    void updateBaseDictionaryMatch(@Param("param") BaseDictionaryMatch baseDictionaryMatch);

    /**
     * 删除
     * @param baseDictionaryMatch
     */
    void deleteBaseDictionaryMatch(@Param("param")BaseDictionaryMatch baseDictionaryMatch);
}
