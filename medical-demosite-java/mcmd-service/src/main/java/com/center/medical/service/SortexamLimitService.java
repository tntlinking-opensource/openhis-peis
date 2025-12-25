package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.SortexamLimit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 每日排检上限(SortexamLimit)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:19
 */
public interface SortexamLimitService extends IService<SortexamLimit> {

    /**
     * 分页查询[每日排检上限]列表
     *
     * @param page  分页参数
     * @param param SortexamLimit查询参数
     * @return 分页数据
     */
    IPage<SortexamLimit> getList(PageParam<SortexamLimit> page, SortexamLimit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    SortexamLimit getInfoById(String id);

}

