package com.center.medical.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseDictionaryClass;

/**
 * 字典类型(BaseDictionaryClass)表服务接口
 *
 * @author ay
 * @since 2022-11-18 18:16:14
 */
public interface BaseDictionaryClassService extends IService<BaseDictionaryClass> {

    /**
    * 分页查询[字典类型]列表
    *
    * @param page 分页参数
    * @param param 查询参数
    * @return 分页数据
    */
    IPage<BaseDictionaryClass> getList(PageParam<BaseDictionaryClass> page, BaseDictionaryClass param);

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    BaseDictionaryClass getInfoById(String id);

}

