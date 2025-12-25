package com.center.medical.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.ReportShareTwo;
import com.center.medical.common.utils.page.PageParam;

/**
 * 报告分享和报告关系表(ReportShareTwo)服务接口
 *
 * @author ay
 * @since 2023-09-19 16:23:51
 */
public interface ReportShareTwoService extends IService<ReportShareTwo> {

    /**
     * 分页查询[报告分享和报告关系表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReportShareTwo> getPage(PageParam<ReportShareTwo> page, ReportShareTwo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReportShareTwo getInfoById(String id);

}

