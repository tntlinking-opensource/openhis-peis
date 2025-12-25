package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.BaseIndustry;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 国民经济行业分类GB/T 4754—2017(BaseIndustry)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:12:36
 */
public interface BaseIndustryService extends IService<BaseIndustry> {

    /**
     * 分页查询[国民经济行业分类GB/T 4754—2017]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<BaseIndustry> getPage(PageParam<BaseIndustry> page, BaseIndustry param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BaseIndustry getInfoById(String id);

}

