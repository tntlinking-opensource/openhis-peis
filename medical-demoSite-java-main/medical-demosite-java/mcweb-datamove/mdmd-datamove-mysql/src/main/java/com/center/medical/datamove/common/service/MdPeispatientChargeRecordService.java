package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPeispatientChargeRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 收费记录(MdPeispatientChargeRecord)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:12
 */
public interface MdPeispatientChargeRecordService extends IService<MdPeispatientChargeRecord> {

    /**
     * 分页查询[收费记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientChargeRecord> getPage(PageParam<MdPeispatientChargeRecord> page, MdPeispatientChargeRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientChargeRecord getInfoById(String id);

}

