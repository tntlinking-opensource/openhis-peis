package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Peisorgreservationreceipt;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * QT发票表(Peisorgreservationreceipt)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:23:08
 */
public interface PeisorgreservationreceiptService extends IService<Peisorgreservationreceipt> {

    /**
     * 分页查询[QT发票表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Peisorgreservationreceipt> getPage(PageParam<Peisorgreservationreceipt> page, Peisorgreservationreceipt param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peisorgreservationreceipt getInfoById(String id);

}

