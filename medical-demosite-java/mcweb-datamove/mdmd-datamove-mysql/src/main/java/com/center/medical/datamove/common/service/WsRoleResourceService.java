package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.WsRoleResource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 角色-资源映射表(WsRoleResource)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:42
 */
public interface WsRoleResourceService extends IService<WsRoleResource> {

    /**
     * 分页查询[角色-资源映射表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<WsRoleResource> getPage(PageParam<WsRoleResource> page, WsRoleResource param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键roleId
     * @return 详情信息
     */
    WsRoleResource getInfoById(String id);

}

