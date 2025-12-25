package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdBaseIndustry;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 国民经济行业分类GB/T 4754—2017(MdBaseIndustry)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:10
 */
public interface MdBaseIndustryService extends IService<MdBaseIndustry> {

    /**
     * 分页查询[国民经济行业分类GB/T 4754—2017]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdBaseIndustry> getPage(PageParam<MdBaseIndustry> page, MdBaseIndustry param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBaseIndustry getInfoById(String id);

}

