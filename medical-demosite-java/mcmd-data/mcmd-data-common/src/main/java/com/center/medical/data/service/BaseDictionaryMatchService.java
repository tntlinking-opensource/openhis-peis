package com.center.medical.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseDictionaryMatch;
import com.center.medical.data.bean.param.BaseDictionaryParam;

/**
 * 体检系统-职业卫生管理平台 字典匹配(BaseDictionaryMatch)表服务接口
 *
 * @author ay
 * @since 2022-11-18 18:16:14
 */
public interface BaseDictionaryMatchService extends IService<BaseDictionaryMatch> {

    /**
    * 分页查询[体检系统-职业卫生管理平台 字典匹配]列表
    *
    * @param page 分页参数
    * @param param 查询参数
    * @return 分页数据
    */
    IPage<BaseDictionaryMatch> getList(PageParam<BaseDictionaryMatch> page, BaseDictionaryMatch param);

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    BaseDictionaryMatch getInfoById(String id);

    /**
     * 通过搜索条件分页查询列表
     * @param page
     * @param baseDictionaryParam
     * @return
     */
    PageParam<BaseDictionaryMatch> getDataMatch(PageParam<BaseDictionaryMatch> page, BaseDictionaryParam baseDictionaryParam);

    /**
     * 建立或更新数据字典匹配
     * @param baseDictionaryMatch
     * @return
     */
    Boolean setDataMatch(BaseDictionaryMatch baseDictionaryMatch);
}

