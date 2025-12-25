package com.center.medical.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.ReportUrl;

import java.util.List;

/**
 * BG科室报告目录表(ReportUrl)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-23 17:11:14
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

    /**
     * 根据体检号获取记录详情
     *
     * @param patientcode 体检号
     * @return 详情信息
     */
    List<ReportUrl> getList(String patientcode, int isHead, int diseaseHealth);
}

