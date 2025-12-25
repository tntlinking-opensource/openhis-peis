package com.center.medical.datamove.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Createmeal;

/**
 * 普通套餐表(Createmeal)服务接口
 *
 * @author ay
 * @since 2023-07-25 21:56:07
 */
public interface OrCreatemealService extends IService<Createmeal> {

    /**
     * 分页查询[普通套餐表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Createmeal> getPage(PageParam<Createmeal> page, Createmeal param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Createmeal getInfoById(String id);

}

