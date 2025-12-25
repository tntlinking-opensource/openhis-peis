package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdItemsAndFzx;

/**
 * JC收费项目和分中心关联表(MdItemsAndFzx)服务接口
 *
 * @author ay
 * @since 2024-07-13 13:45:55
 */
public interface MdItemsAndFzxService extends IService<MdItemsAndFzx> {

    /**
     * 分页查询[JC收费项目和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdItemsAndFzx> getPage(PageParam<MdItemsAndFzx> page, MdItemsAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdItemsAndFzx getInfoById(String id);

}

