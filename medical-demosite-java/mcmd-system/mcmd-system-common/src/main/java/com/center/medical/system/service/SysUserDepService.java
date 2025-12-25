package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysUserDep;

/**
 * 系统用户关联的科室(SysUserDep)表服务接口
 *
 * @author ay
 * @since 2023-04-06 13:33:20
 */
public interface SysUserDepService extends IService<SysUserDep> {

    /**
     * 分页查询[系统用户关联的科室]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysUserDep> getList(PageParam<SysUserDep> page, SysUserDep param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysUserDep getInfoById(String id);
    
}

