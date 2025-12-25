package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.QxWsResourceMapper;
import com.center.medical.datamove.oracle.bean.model.QxWsResource;
import com.center.medical.datamove.oracle.service.QxWsResourceService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (QxWsResource)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:04
 */
@Slf4j
@Service("qxWsResourceService")
@RequiredArgsConstructor
public class QxWsResourceServiceImpl extends ServiceImpl<QxWsResourceMapper, QxWsResource> implements QxWsResourceService {

    private final QxWsResourceMapper qxWsResourceMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxWsResource查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QxWsResource> getPage(PageParam<QxWsResource> page, QxWsResource param) {
        return qxWsResourceMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public QxWsResource getInfoById(String id) {
        return qxWsResourceMapper.getInfoById(id);
    }

}


