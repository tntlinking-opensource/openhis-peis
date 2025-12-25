package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdItems;

/**
 * JC收费项目表(MdItems)服务接口
 *
 * @author ay
 * @since 2024-07-13 13:45:54
 */
public interface MdItemsService extends IService<MdItems> {

    /**
     * 分页查询[JC收费项目表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdItems> getPage(PageParam<MdItems> page, MdItems param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdItems getInfoById(String id);

}

