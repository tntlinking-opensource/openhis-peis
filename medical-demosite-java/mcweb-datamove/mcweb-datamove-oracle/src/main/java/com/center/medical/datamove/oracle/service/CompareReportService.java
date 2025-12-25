package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.CompareReport;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 对比报告表，用于存储对报告都有哪些生成过(CompareReport)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:17:45
 */
public interface CompareReportService extends IService<CompareReport> {

    /**
     * 分页查询[对比报告表，用于存储对报告都有哪些生成过]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CompareReport> getPage(PageParam<CompareReport> page, CompareReport param);


}

