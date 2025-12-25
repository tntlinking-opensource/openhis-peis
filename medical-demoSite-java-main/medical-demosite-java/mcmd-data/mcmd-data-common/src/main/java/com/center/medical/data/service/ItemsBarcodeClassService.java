package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ItemsBarcodeClass;

/**
 * 收费项目条码打印分类(ItemsBarcodeClass)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-18 10:27:20
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

