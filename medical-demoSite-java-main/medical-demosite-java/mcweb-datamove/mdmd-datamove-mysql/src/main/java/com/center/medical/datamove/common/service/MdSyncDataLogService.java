package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdSyncDataLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 同步数据操作记录(MdSyncDataLog)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:51
 */
public interface MdSyncDataLogService extends IService<MdSyncDataLog> {

    /**
     * 分页查询[同步数据操作记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdSyncDataLog> getPage(PageParam<MdSyncDataLog> page, MdSyncDataLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键logId
     * @return 详情信息
     */
    MdSyncDataLog getInfoById(Long id);

}

