package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysFunction;

/**
 * 系统业务功能(SysFunction)服务接口
 *
 * @author makejava
 * @since 2024-03-19 11:12:08
 */
public interface SysFunctionService extends IService<SysFunction> {

    /**
     * 分页查询[系统业务功能]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysFunction> getPage(PageParam<SysFunction> page, SysFunction param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键functionId
     * @return 详情信息
     */
    SysFunction getInfoById(String id);

}

