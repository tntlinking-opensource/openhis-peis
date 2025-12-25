package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.WsRoleResourceMapper;
import com.center.medical.datamove.common.bean.model.WsRoleResource;
import com.center.medical.datamove.common.service.WsRoleResourceService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 角色-资源映射表(WsRoleResource)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:42
 */
@Slf4j
@Service("wsRoleResourceService")
@RequiredArgsConstructor
public class WsRoleResourceServiceImpl extends ServiceImpl<WsRoleResourceMapper, WsRoleResource> implements WsRoleResourceService {

    private final WsRoleResourceMapper wsRoleResourceMapper;

    /**
     * 分页查询[角色-资源映射表]列表
     *
     * @param page  分页参数
     * @param param WsRoleResource查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WsRoleResource> getPage(PageParam<WsRoleResource> page, WsRoleResource param) {
        return wsRoleResourceMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键roleId
     * @return 详情信息
     */
    @Override
    public WsRoleResource getInfoById(String id) {
        return wsRoleResourceMapper.getInfoById(id);
    }

}


