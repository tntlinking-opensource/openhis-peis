package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.SysDictData;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 字典数据表(SysDictData)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:34
 */
public interface SysDictDataService extends IService<SysDictData> {

    /**
     * 分页查询[字典数据表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysDictData> getPage(PageParam<SysDictData> page, SysDictData param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键dictCode
     * @return 详情信息
     */
    SysDictData getInfoById(Long id);

}

