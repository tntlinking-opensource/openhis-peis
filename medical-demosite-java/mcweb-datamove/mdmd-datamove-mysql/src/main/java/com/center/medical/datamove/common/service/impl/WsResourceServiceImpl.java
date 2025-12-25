package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.WsResourceMapper;
import com.center.medical.datamove.common.bean.model.WsResource;
import com.center.medical.datamove.common.service.WsResourceService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 网站资源(WsResource)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:41
 */
@Slf4j
@Service("wsResourceService")
@RequiredArgsConstructor
public class WsResourceServiceImpl extends ServiceImpl<WsResourceMapper, WsResource> implements WsResourceService {

    private final WsResourceMapper wsResourceMapper;

    /**
     * 分页查询[网站资源]列表
     *
     * @param page  分页参数
     * @param param WsResource查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WsResource> getPage(PageParam<WsResource> page, WsResource param) {
        return wsResourceMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public WsResource getInfoById(String id) {
        return wsResourceMapper.getInfoById(id);
    }

}


