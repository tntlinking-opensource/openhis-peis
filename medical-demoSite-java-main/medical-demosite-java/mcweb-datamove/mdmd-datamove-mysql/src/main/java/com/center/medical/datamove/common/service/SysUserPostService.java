package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.SysUserPost;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 用户与岗位关联表(SysUserPost)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:40
 */
public interface SysUserPostService extends IService<SysUserPost> {

    /**
     * 分页查询[用户与岗位关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysUserPost> getPage(PageParam<SysUserPost> page, SysUserPost param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    SysUserPost getInfoById(Long id);

}

