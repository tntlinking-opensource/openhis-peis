package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.ReportUrl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * BG科室报告目录表(ReportUrl)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:18
 */
public interface ReportUrlService extends IService<ReportUrl> {

    /**
     * 分页查询[BG科室报告目录表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReportUrl> getPage(PageParam<ReportUrl> page, ReportUrl param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReportUrl getInfoById(String id);

}

