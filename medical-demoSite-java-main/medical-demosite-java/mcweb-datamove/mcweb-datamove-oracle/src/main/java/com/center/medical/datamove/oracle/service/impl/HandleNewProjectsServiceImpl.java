package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.HandleNewProjectsMapper;
import com.center.medical.datamove.oracle.bean.model.HandleNewProjects;
import com.center.medical.datamove.oracle.service.HandleNewProjectsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS检验科加项处理(HandleNewProjects)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:10
 */
@Slf4j
@Service("handleNewProjectsService")
@RequiredArgsConstructor
public class HandleNewProjectsServiceImpl extends ServiceImpl<HandleNewProjectsMapper, HandleNewProjects> implements HandleNewProjectsService {

    private final HandleNewProjectsMapper handleNewProjectsMapper;

    /**
     * 分页查询[KS检验科加项处理]列表
     *
     * @param page  分页参数
     * @param param HandleNewProjects查询参数
     * @return 分页数据
     */
    @Override
    public IPage<HandleNewProjects> getPage(PageParam<HandleNewProjects> page, HandleNewProjects param) {
        return handleNewProjectsMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public HandleNewProjects getInfoById(String id) {
        return handleNewProjectsMapper.getInfoById(id);
    }

}


