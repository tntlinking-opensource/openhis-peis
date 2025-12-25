package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.QxWsRoleResourceMapper;
import com.center.medical.datamove.oracle.bean.model.QxWsRoleResource;
import com.center.medical.datamove.oracle.service.QxWsRoleResourceService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (QxWsRoleResource)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:06
 */
@Slf4j
@Service("qxWsRoleResourceService")
@RequiredArgsConstructor
public class QxWsRoleResourceServiceImpl extends ServiceImpl<QxWsRoleResourceMapper, QxWsRoleResource> implements QxWsRoleResourceService {

    private final QxWsRoleResourceMapper qxWsRoleResourceMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxWsRoleResource查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QxWsRoleResource> getPage(PageParam<QxWsRoleResource> page, QxWsRoleResource param) {
        return qxWsRoleResourceMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键roleId
     * @return 详情信息
     */
    @Override
    public QxWsRoleResource getInfoById(Object id) {
        return qxWsRoleResourceMapper.getInfoById(id);
    }

}


