package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.QxResourceMapper;
import com.center.medical.datamove.oracle.bean.model.QxResource;
import com.center.medical.datamove.oracle.service.QxResourceService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (QxResource)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:48
 */
@Slf4j
@Service("qxResourceService")
@RequiredArgsConstructor
public class QxResourceServiceImpl extends ServiceImpl<QxResourceMapper, QxResource> implements QxResourceService {

    private final QxResourceMapper qxResourceMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxResource查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QxResource> getPage(PageParam<QxResource> page, QxResource param) {
        return qxResourceMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public QxResource getInfoById(String id) {
        return qxResourceMapper.getInfoById(id);
    }

}


