package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdHandleNewProjectsMapper;
import com.center.medical.datamove.common.bean.model.MdHandleNewProjects;
import com.center.medical.datamove.common.service.MdHandleNewProjectsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS检验科加项处理(MdHandleNewProjects)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:21
 */
@Slf4j
@Service("mdHandleNewProjectsService")
@RequiredArgsConstructor
public class MdHandleNewProjectsServiceImpl extends ServiceImpl<MdHandleNewProjectsMapper, MdHandleNewProjects> implements MdHandleNewProjectsService {

    private final MdHandleNewProjectsMapper mdHandleNewProjectsMapper;

    /**
     * 分页查询[KS检验科加项处理]列表
     *
     * @param page  分页参数
     * @param param MdHandleNewProjects查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdHandleNewProjects> getPage(PageParam<MdHandleNewProjects> page, MdHandleNewProjects param) {
        return mdHandleNewProjectsMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdHandleNewProjects getInfoById(String id) {
        return mdHandleNewProjectsMapper.getInfoById(id);
    }

    ;

}


