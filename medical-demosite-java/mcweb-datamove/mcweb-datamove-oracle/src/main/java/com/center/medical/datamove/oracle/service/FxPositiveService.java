package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.FxPositive;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (FxPositive)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:18:57
 */
public interface FxPositiveService extends IService<FxPositive> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<FxPositive> getPage(PageParam<FxPositive> page, FxPositive param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    FxPositive getInfoById(String id);

}

