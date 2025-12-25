package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.SysUploadType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 数据上传接收日志数据类型(SysUploadType)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:25:09
 */
public interface SysUploadTypeService extends IService<SysUploadType> {

    /**
     * 分页查询[数据上传接收日志数据类型]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysUploadType> getPage(PageParam<SysUploadType> page, SysUploadType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysUploadType getInfoById(String id);

}

