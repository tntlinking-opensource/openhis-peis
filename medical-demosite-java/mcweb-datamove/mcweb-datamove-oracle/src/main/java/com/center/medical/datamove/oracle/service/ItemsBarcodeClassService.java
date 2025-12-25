package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.ItemsBarcodeClass;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 收费项目条码打印分类(ItemsBarcodeClass)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:19:23
 */
public interface ItemsBarcodeClassService extends IService<ItemsBarcodeClass> {

    /**
     * 分页查询[收费项目条码打印分类]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ItemsBarcodeClass> getPage(PageParam<ItemsBarcodeClass> page, ItemsBarcodeClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ItemsBarcodeClass getInfoById(String id);

}

