package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.InspectReport;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 检验报告生成记录(InspectReport)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:48
 */
public interface InspectReportService extends IService<InspectReport> {

    /**
     * 分页查询[检验报告生成记录]列表
     *
     * @param page  分页参数
     * @param param InspectReport查询参数
     * @return 分页数据
     */
    IPage<InspectReport> getList(PageParam<InspectReport> page, InspectReport param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    InspectReport getInfoById(String id);

}

