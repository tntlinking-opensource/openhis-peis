package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdReportUrl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * BG科室报告目录表(MdReportUrl)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:17
 */
public interface MdReportUrlService extends IService<MdReportUrl> {

    /**
     * 分页查询[BG科室报告目录表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdReportUrl> getPage(PageParam<MdReportUrl> page, MdReportUrl param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdReportUrl getInfoById(String id);

}

