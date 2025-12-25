package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.StencilMaintain;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 用于保存科室的模板（个检用）、团检的模板、对比模板(StencilMaintain)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:25:05
 */
public interface StencilMaintainService extends IService<StencilMaintain> {

    /**
     * 分页查询[用于保存科室的模板（个检用）、团检的模板、对比模板]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<StencilMaintain> getPage(PageParam<StencilMaintain> page, StencilMaintain param);


}

