package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.SmsRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KF短信通知记录(SmsRecord)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:25:01
 */
public interface SmsRecordService extends IService<SmsRecord> {

    /**
     * 分页查询[KF短信通知记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SmsRecord> getPage(PageParam<SmsRecord> page, SmsRecord param);


}

