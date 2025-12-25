package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdSmsRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 短信发送记录(MdSmsRecord)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:44
 */
public interface MdSmsRecordService extends IService<MdSmsRecord> {

    /**
     * 分页查询[短信发送记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdSmsRecord> getPage(PageParam<MdSmsRecord> page, MdSmsRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSmsRecord getInfoById(String id);

}

