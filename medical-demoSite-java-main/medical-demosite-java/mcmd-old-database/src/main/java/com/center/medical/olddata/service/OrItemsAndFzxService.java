package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrItemsAndFzx;

import java.util.List;

/**
 * JC收费项目和分中心关联表(ItemsAndFzx)服务接口
 *
 * @author ay
 * @since 2024-07-13 14:27:29
 */
public interface OrItemsAndFzxService extends IService<OrItemsAndFzx> {

    /**
     * 分页查询[JC收费项目和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrItemsAndFzx> getPage(PageParam<OrItemsAndFzx> page, OrItemsAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrItemsAndFzx getInfoById(String id);

    /**
     * 通过收费项目id查询
     * @param itemsId
     * @return
     */
    List<OrItemsAndFzx> getInfoByitemsId(String itemsId);
}

