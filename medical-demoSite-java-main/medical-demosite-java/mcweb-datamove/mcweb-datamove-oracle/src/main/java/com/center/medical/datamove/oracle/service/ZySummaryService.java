package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.ZySummary;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC职业病检查结论(ZySummary)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:31:02
 */
public interface ZySummaryService extends IService<ZySummary> {

    /**
     * 分页查询[JC职业病检查结论]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ZySummary> getPage(PageParam<ZySummary> page, ZySummary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ZySummary getInfoById(String id);

}

