package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.BaseDictionary;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 字典(BaseDictionary)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:12:28
 */
public interface BaseDictionaryService extends IService<BaseDictionary> {

    /**
     * 分页查询[字典]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<BaseDictionary> getPage(PageParam<BaseDictionary> page, BaseDictionary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BaseDictionary getInfoById(String id);

}

