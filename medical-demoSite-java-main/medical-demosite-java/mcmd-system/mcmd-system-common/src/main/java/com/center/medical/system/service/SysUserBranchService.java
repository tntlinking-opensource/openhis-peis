package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysUserBranch;
import com.center.medical.system.bean.param.AddSysUserBranchParam;

/**
 * 系统用户关联的分中心(SysUserBranch)表服务接口
 *
 * @author 路飞船长
 * @since 2023-05-20 15:27:58
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

    /**
     * 批量添加用户和分中心
     * @param param
     * @return
     */
    Boolean addSysUserBranch(AddSysUserBranchParam param);
}

