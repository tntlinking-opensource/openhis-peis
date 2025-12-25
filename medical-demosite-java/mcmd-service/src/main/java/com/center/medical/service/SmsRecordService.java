package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.SmsRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.param.SmsDataParam;
import com.center.medical.common.utils.page.PageParam;

/**
 * 短信发送记录(SmsRecord)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:12
 */
public interface SmsRecordService extends IService<SmsRecord> {

    /**
     * 分页查询[短信发送记录]列表
     *
     * @param page  分页参数
     * @param param SmsRecord查询参数
     * @return 分页数据
     */
    IPage<SmsRecord> getList(PageParam<SmsRecord> page, SmsRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    SmsRecord getInfoById(String id);

    /**
     * 查看短信数据
     * @param page
     * @param param
     * @return
     */
    IPage<SmsRecord> getSmsData(PageParam<SmsRecord> page, SmsDataParam param);
}

