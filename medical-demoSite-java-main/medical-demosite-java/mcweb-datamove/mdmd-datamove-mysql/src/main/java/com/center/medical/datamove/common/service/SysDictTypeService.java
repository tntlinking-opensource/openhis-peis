package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.SysDictType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 字典类型表(SysDictType)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:35
 */
public interface SysDictTypeService extends IService<SysDictType> {

    /**
     * 分页查询[字典类型表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysDictType> getPage(PageParam<SysDictType> page, SysDictType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键dictId
     * @return 详情信息
     */
    SysDictType getInfoById(Long id);

}

