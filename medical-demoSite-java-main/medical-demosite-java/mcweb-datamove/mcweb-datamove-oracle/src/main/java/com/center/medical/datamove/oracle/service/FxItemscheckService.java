package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.FxItemscheck;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (FxItemscheck)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:18:53
 */
public interface FxItemscheckService extends IService<FxItemscheck> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<FxItemscheck> getPage(PageParam<FxItemscheck> page, FxItemscheck param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    FxItemscheck getInfoById(String id);

}

