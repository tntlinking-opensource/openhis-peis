package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Chest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 此表为通用表，团检(Chest)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:17:37
 */
public interface ChestService extends IService<Chest> {

    /**
     * 分页查询[此表为通用表，团检]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Chest> getPage(PageParam<Chest> page, Chest param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Chest getInfoById(String id);

}

