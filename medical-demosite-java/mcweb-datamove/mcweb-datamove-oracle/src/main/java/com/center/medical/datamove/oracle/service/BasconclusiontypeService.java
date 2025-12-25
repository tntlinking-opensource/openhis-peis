package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Basconclusiontype;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (Basconclusiontype)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:12:24
 */
public interface BasconclusiontypeService extends IService<Basconclusiontype> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Basconclusiontype> getPage(PageParam<Basconclusiontype> page, Basconclusiontype param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Basconclusiontype getInfoById(String id);

}

