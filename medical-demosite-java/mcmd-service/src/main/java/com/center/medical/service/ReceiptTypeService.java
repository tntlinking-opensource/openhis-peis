package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.ReceiptType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 发票类型(ReceiptType)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:25
 */
public interface ReceiptTypeService extends IService<ReceiptType> {

    /**
     * 分页查询[发票类型]列表
     *
     * @param page  分页参数
     * @param param ReceiptType查询参数
     * @return 分页数据
     */
    IPage<ReceiptType> getList(PageParam<ReceiptType> page, ReceiptType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    ReceiptType getInfoById(String id);

}

