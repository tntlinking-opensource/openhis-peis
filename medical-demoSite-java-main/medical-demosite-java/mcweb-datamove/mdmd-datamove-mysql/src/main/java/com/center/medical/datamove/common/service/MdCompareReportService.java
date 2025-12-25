package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdCompareReport;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 对比报告表，用于存储对报告都有哪些生成过(MdCompareReport)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:18
 */
public interface MdCompareReportService extends IService<MdCompareReport> {

    /**
     * 分页查询[对比报告表，用于存储对报告都有哪些生成过]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdCompareReport> getPage(PageParam<MdCompareReport> page, MdCompareReport param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCompareReport getInfoById(String id);

}

