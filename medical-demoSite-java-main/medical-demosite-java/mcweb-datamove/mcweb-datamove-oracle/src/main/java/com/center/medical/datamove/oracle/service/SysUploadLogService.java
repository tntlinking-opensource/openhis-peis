package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.SysUploadLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 数据上传接收日志(SysUploadLog)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:25:07
 */
public interface SysUploadLogService extends IService<SysUploadLog> {

    /**
     * 分页查询[数据上传接收日志]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysUploadLog> getPage(PageParam<SysUploadLog> page, SysUploadLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysUploadLog getInfoById(String id);

}

