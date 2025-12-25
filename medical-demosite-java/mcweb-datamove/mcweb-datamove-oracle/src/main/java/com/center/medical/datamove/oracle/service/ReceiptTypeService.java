package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.ReceiptType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 发票类型(ReceiptType)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:13
 */
public interface ReceiptTypeService extends IService<ReceiptType> {

    /**
     * 分页查询[发票类型]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReceiptType> getPage(PageParam<ReceiptType> page, ReceiptType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReceiptType getInfoById(String id);

}

