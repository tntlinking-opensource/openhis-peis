package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.QxRoleResourceMapper;
import com.center.medical.datamove.oracle.bean.model.QxRoleResource;
import com.center.medical.datamove.oracle.service.QxRoleResourceService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (QxRoleResource)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:50
 */
@Slf4j
@Service("qxRoleResourceService")
@RequiredArgsConstructor
public class QxRoleResourceServiceImpl extends ServiceImpl<QxRoleResourceMapper, QxRoleResource> implements QxRoleResourceService {

    private final QxRoleResourceMapper qxRoleResourceMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxRoleResource查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QxRoleResource> getPage(PageParam<QxRoleResource> page, QxRoleResource param) {
        return qxRoleResourceMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键roleId
     * @return 详情信息
     */
    @Override
    public QxRoleResource getInfoById(Object id) {
        return qxRoleResourceMapper.getInfoById(id);
    }

}


