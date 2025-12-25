package com.center.medical.sync.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sync.bean.model.SyncDataType;

/**
 * 同步数据类型(SyncDataType)表服务接口
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:34
 */
public interface SyncDataTypeService extends IService<SyncDataType> {

    /**
     * 分页查询[同步数据类型]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SyncDataType> getPage(PageParam<SyncDataType> page, SyncDataType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键typeId
     * @return 详情信息
     */
    SyncDataType getInfoById(Integer id);

    void updateDiy(SyncDataType syncDataType);
}

