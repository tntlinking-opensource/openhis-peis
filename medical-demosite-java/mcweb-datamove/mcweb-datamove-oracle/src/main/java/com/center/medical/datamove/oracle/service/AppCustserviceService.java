package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.AppCustservice;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (AppCustservice)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:11:28
 */
public interface AppCustserviceService extends IService<AppCustservice> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AppCustservice> getPage(PageParam<AppCustservice> page, AppCustservice param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppCustservice getInfoById(String id);

}

