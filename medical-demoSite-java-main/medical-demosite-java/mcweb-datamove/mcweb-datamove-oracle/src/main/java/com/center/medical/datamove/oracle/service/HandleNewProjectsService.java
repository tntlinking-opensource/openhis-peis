package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.HandleNewProjects;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS检验科加项处理(HandleNewProjects)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:19:09
 */
public interface HandleNewProjectsService extends IService<HandleNewProjects> {

    /**
     * 分页查询[KS检验科加项处理]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<HandleNewProjects> getPage(PageParam<HandleNewProjects> page, HandleNewProjects param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    HandleNewProjects getInfoById(String id);

}

