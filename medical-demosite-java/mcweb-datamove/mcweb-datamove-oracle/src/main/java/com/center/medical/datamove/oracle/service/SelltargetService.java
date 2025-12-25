package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Selltarget;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * XS销售目标(Selltarget)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:57
 */
public interface SelltargetService extends IService<Selltarget> {

    /**
     * 分页查询[XS销售目标]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Selltarget> getPage(PageParam<Selltarget> page, Selltarget param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Selltarget getInfoById(String id);

}

