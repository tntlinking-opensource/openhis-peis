package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.SysUserBranch;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 系统用户关联的分中心(SysUserBranch)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:39
 */
public interface SysUserBranchService extends IService<SysUserBranch> {

    /**
     * 分页查询[系统用户关联的分中心]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysUserBranch> getPage(PageParam<SysUserBranch> page, SysUserBranch param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysUserBranch getInfoById(String id);

}

