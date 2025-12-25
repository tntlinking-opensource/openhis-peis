package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.BatchSmsRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 批量发送短信记录(BatchSmsRecord)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:12:48
 */
public interface BatchSmsRecordService extends IService<BatchSmsRecord> {

    /**
     * 分页查询[批量发送短信记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<BatchSmsRecord> getPage(PageParam<BatchSmsRecord> page, BatchSmsRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BatchSmsRecord getInfoById(String id);

}

