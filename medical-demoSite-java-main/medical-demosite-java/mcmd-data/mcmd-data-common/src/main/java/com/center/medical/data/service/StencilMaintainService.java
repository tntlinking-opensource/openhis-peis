package com.center.medical.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.StencilMaintain;

/**
 * 模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)(StencilMaintain)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:06
 */
public interface StencilMaintainService extends IService<StencilMaintain> {

    /**
     * 分页查询[模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)]列表
     *
     * @param page  分页参数
     * @param param StencilMaintain查询参数
     * @return 分页数据
     */
    IPage<StencilMaintain> getList(PageParam<StencilMaintain> page, StencilMaintain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    StencilMaintain getInfoById(String id);

    /**
     * 保存或更新
     * @param stencilMaintain
     * @return
     */
    String saveOrUpdateStencilMaintain(StencilMaintain stencilMaintain);
}

