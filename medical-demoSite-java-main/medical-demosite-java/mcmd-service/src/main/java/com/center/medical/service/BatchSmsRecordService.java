package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.BatchSmsRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 批量发送短信记录(BatchSmsRecord)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:01
 */
public interface BatchSmsRecordService extends IService<BatchSmsRecord> {

    /**
     * 分页查询[批量发送短信记录]列表
     *
     * @param page  分页参数
     * @param param BatchSmsRecord查询参数
     * @return 分页数据
     */
    IPage<BatchSmsRecord> getList(PageParam<BatchSmsRecord> page, BatchSmsRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BatchSmsRecord getInfoById(String id);

}

