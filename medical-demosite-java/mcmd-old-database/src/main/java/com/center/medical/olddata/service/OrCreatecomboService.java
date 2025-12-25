package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrCreatecombo;

/**
 * 套餐表(Createcombo)服务接口
 *
 * @author ay
 * @since 2023-07-25 21:56:43
 */
public interface OrCreatecomboService extends IService<OrCreatecombo> {

    /**
     * 分页查询[套餐表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrCreatecombo> getPage(PageParam<OrCreatecombo> page, OrCreatecombo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrCreatecombo getInfoById(String id);

}

