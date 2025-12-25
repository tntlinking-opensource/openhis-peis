package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Report;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * BG报告主表(Report)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:16
 */
public interface ReportService extends IService<Report> {

    /**
     * 分页查询[BG报告主表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Report> getPage(PageParam<Report> page, Report param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Report getInfoById(String id);

}

