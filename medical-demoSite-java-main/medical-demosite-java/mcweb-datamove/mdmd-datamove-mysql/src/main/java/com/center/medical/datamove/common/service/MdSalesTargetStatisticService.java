package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdSalesTargetStatistic;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 销售目标自动统计(MdSalesTargetStatistic)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:21
 */
public interface MdSalesTargetStatisticService extends IService<MdSalesTargetStatistic> {

    /**
     * 分页查询[销售目标自动统计]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdSalesTargetStatistic> getPage(PageParam<MdSalesTargetStatistic> page, MdSalesTargetStatistic param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSalesTargetStatistic getInfoById(String id);

}

