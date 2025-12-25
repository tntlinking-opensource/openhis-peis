package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.BaseDictionaryClass;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 字典类型(BaseDictionaryClass)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:44:56
 */
public interface BaseDictionaryClassService extends IService<BaseDictionaryClass> {

    /**
     * 分页查询[字典类型]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<BaseDictionaryClass> getPage(PageParam<BaseDictionaryClass> page, BaseDictionaryClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BaseDictionaryClass getInfoById(String id);

}

