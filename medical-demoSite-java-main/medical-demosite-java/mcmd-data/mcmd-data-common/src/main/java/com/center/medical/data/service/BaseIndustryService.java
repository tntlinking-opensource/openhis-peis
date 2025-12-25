package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseIndustry;

/**
 * 国民经济行业分类GB/T 4754—2017(BaseIndustry)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:51
 */
public interface BaseIndustryService extends IService<BaseIndustry> {

    /**
     * 分页查询[国民经济行业分类GB/T 4754—2017]列表
     *
     * @param page  分页参数
     * @param param BaseIndustry查询参数
     * @return 分页数据
     */
    IPage<BaseIndustry> getList(PageParam<BaseIndustry> page, BaseIndustry param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BaseIndustry getInfoById(String id);

}

