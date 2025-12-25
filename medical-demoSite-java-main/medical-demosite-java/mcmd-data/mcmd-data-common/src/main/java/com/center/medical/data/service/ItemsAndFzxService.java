package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ItemsAndFzx;

/**
 * JC收费项目和分中心关联表(ItemsAndFzx)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 11:02:14
 */
public interface ItemsAndFzxService extends IService<ItemsAndFzx> {

    /**
     * 分页查询[JC收费项目和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param ItemsAndFzx查询参数
     * @return 分页数据
     */
    IPage<ItemsAndFzx> getList(PageParam<ItemsAndFzx> page, ItemsAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id ItemsAndFzx查询参数
     * @return 分页数据
     */
    ItemsAndFzx getInfoById(String id);

}

