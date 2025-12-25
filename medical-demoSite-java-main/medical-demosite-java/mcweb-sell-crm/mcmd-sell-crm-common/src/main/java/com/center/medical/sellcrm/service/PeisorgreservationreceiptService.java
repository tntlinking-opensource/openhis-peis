package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Peisorgreservationreceipt;

/**
 * 发票(Peisorgreservationreceipt)表服务接口
 *
 * @author 路飞船长
 * @since 2022-12-02 11:41:03
 */
public interface PeisorgreservationreceiptService extends IService<Peisorgreservationreceipt> {

    /**
     * 分页查询[发票]列表
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

