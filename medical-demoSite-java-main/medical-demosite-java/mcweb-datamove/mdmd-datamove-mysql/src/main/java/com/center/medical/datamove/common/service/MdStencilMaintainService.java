package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdStencilMaintain;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)(MdStencilMaintain)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:48
 */
public interface MdStencilMaintainService extends IService<MdStencilMaintain> {

    /**
     * 分页查询[模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdStencilMaintain> getPage(PageParam<MdStencilMaintain> page, MdStencilMaintain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdStencilMaintain getInfoById(String id);

}

