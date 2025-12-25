package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdSortexamLimit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 每日排检上限(MdSortexamLimit)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:45
 */
public interface MdSortexamLimitService extends IService<MdSortexamLimit> {

    /**
     * 分页查询[每日排检上限]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdSortexamLimit> getPage(PageParam<MdSortexamLimit> page, MdSortexamLimit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSortexamLimit getInfoById(String id);

}

